package com.example.demo.controller;
import com.example.demo.entity.*; import com.example.demo.service.*; import com.example.demo.controller.form.*; import jakarta.validation.Valid; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.validation.BindingResult; import org.springframework.web.bind.annotation.*; import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller @RequestMapping("/admin") public class AdminController {private final MovieService movies;public AdminController(MovieService m){movies=m;}
 @GetMapping String dashboard(Model m){m.addAttribute("latest",movies.admin(0));return "admin/dashboard";}
 @GetMapping("/movies") String list(@RequestParam(defaultValue="0")int page,Model m){m.addAttribute("movies",movies.admin(page));return "admin/movies/list";}
 @GetMapping("/movies/new") String create(Model m){m.addAttribute("movieForm",new MovieForm());m.addAttribute("statuses",MovieStatus.values());return "admin/movies/form";}
 @PostMapping("/movies") String save(@Valid @ModelAttribute MovieForm movieForm,BindingResult br,Model m,RedirectAttributes ra){if(br.hasErrors()){m.addAttribute("statuses",MovieStatus.values());return "admin/movies/form";}movies.save(null,movieForm);ra.addFlashAttribute("message","記事を保存しました");return "redirect:/admin/movies";}
 @GetMapping("/movies/{id}/edit") String edit(@PathVariable Long id,Model m){m.addAttribute("movieId",id);m.addAttribute("movieForm",movies.form(movies.get(id)));m.addAttribute("statuses",MovieStatus.values());return "admin/movies/form";}
 @PostMapping("/movies/{id}") String update(@PathVariable Long id,@Valid @ModelAttribute MovieForm movieForm,BindingResult br,Model m,RedirectAttributes ra){if(br.hasErrors()){m.addAttribute("movieId",id);m.addAttribute("statuses",MovieStatus.values());return "admin/movies/form";}movies.save(id,movieForm);ra.addFlashAttribute("message","記事を更新しました");return "redirect:/admin/movies";}
 @PostMapping("/movies/{id}/delete") String delete(@PathVariable Long id,RedirectAttributes ra){movies.delete(id);ra.addFlashAttribute("message","記事を削除しました");return "redirect:/admin/movies";}
}
