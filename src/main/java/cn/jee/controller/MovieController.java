package cn.jee.controller;


import cn.jee.entity.Movie;
import cn.jee.entity.User;
import cn.jee.repository.MovieRepository;
import cn.jee.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MovieRepository movieRepository;

  // 1.2 登录页
  @GetMapping("/index")
  public String index() {
    return "index";
  }

  // 1.3 登录
  @PostMapping("/login")
  public String login(String name, HttpSession session) {

    User user = userRepository.findByName(name);

    if (user == null) {
      user = new User();
      user.setName(name);
      userRepository.save(user);
    }

    session.setAttribute("user", user);

    return "redirect:/movie/list";
  }

  // 2.1 列表
  @GetMapping("/list")
  public String list(Model model, HttpSession session) {

    User user = (User) session.getAttribute("user");

    if (user == null) {
      return "redirect:/movie/index";
    }

    List<Movie> list = movieRepository.findByUserId(user.getId());

    model.addAttribute("movies", list);
    return "list";
  }

  // 4.1 新增页面
  @GetMapping("/addPage")
  public String addPage() {
    return "add";
  }

  // 4.3 保存电影
  @PostMapping("/add")
  public String add(@Valid Movie movie,
                    BindingResult br,
                    HttpSession session,
                    Model model,
                    MultipartFile file) throws Exception {

    // 1. 校验失败直接返回
    if (br.hasErrors()) {
      model.addAttribute("errors", br.getAllErrors());
      return "add";
    }

    // 2. 从 session 获取用户
    User sessionUser = (User) session.getAttribute("user");

    if (sessionUser == null) { 
      return "redirect:/movie/index";
    }

    // 3.：从数据库重新加载 User（必须是 JPA 管理对象）
    User user = userRepository.findById(sessionUser.getId())
      .orElse(null);

    if (user == null) {
      return "redirect:/movie/index";
    }

    movie.setUser(user);

    // 4. 初始化 images（防止 null）
    if (movie.getImages() == null) {
      movie.setImages(new ArrayList<>());
    }

    // 5. 保存图片
    if (file != null && !file.isEmpty()) {
      String fileName = file.getOriginalFilename();
      String path = "D:/movie-images/";

      file.transferTo(new java.io.File(path + fileName));

      movie.getImages().add(fileName);
    }

    // 6. 保存电影
    movieRepository.save(movie);

    return "redirect:/movie/list";
  }

  // 5.1 上传页
  @GetMapping("/uploadPage")
  public String uploadPage(Long id, Model model) {
    model.addAttribute("movieId", id);
    return "upload";
  }

  // 5.3 上传
  @PostMapping("/upload")
  public String upload(Long id, MultipartFile file) throws IOException {

    Movie movie = movieRepository.findById(id).get();

    movie.getImages().add(file.getOriginalFilename());

    movieRepository.save(movie);

    return "redirect:/movie/list";
  }

  // 6.1 JSON图片
  @ResponseBody
  @GetMapping("/images")
  public List<String> images(Long id) {
    return movieRepository.findById(id).get().getImages();
  }
}