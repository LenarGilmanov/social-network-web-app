package com.lenar.controller;

import com.lenar.client.PostClient;
import com.lenar.entity.dto.PostDto;
import com.lenar.entity.dto.PostsContainerDto;
import com.lenar.entity.dto.request.CreatePostRequest;
import com.lenar.entity.dto.request.UpdatePostRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommonController {
    private final PostClient postClient;

    public CommonController(PostClient postClient) {
        this.postClient = postClient;
    }

    @GetMapping("/")
    public String redirectToMainPage() {
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String getMainPage(Model model) {
        PostsContainerDto posts = postClient.findAll();
        model.addAttribute("posts", posts.getPosts());

        return "main-page";
    }

    @GetMapping("/posts/{id}")
    public String getPostDetailsPage(@PathVariable(name = "id") int id, Model model) {
        PostDto post = postClient.findById(id);
        model.addAttribute("post", post);
        return "details-page";
    }

    @PostMapping("/posts/create")
    public String createPost(@RequestParam String author,
                             @RequestParam String title) {
        CreatePostRequest request = new CreatePostRequest(author, title);
        postClient.save(request);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{id}/update")
    public String updatePost(@PathVariable int id,
                             @RequestParam String author,
                             @RequestParam String title,
                             @RequestParam int numberOfLikes,
                             @RequestParam int numberOfDislikes) {
        UpdatePostRequest request = new UpdatePostRequest(author, numberOfLikes, numberOfDislikes, title);
        postClient.updateById(id, request);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable int id) {
        postClient.deleteById(id);
        return "redirect:/posts";
    }
}
