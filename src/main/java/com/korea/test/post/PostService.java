package com.korea.test.post;

import com.korea.test.Note.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public void writePost(Note targetNote) {
        Post post = new Post();
        post.setTitle("NewTitle...");
        post.setContent("");
        post.setCreateDate(LocalDateTime.now());
        post.setNote(targetNote);
        postRepository.save(post);
    }

    public Post findByPostId(Long targetPostid) {
        return postRepository.findById(targetPostid).get();
    }

    public void updatePost(Long targetPostid, String title, String content) {
        Post targetPost = findByPostId(targetPostid);
        targetPost.setTitle(title);
        targetPost.setContent(content);
        targetPost.setUpdateDate(LocalDateTime.now());
        postRepository.save(targetPost);
    }

    public void deletePost(Long targetPostid) {
        postRepository.deleteById(targetPostid);
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }
}
