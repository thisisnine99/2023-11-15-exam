package com.korea.test.Note;

import com.korea.test.post.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Note> noteList = noteService.getList();
        if (noteList.isEmpty()) {
            Note note = noteService.createNote();
            postService.writePost(note);
        }

        model.addAttribute("noteList", noteList);
        model.addAttribute("targetNote", noteList.get(0));
        model.addAttribute("postList", noteList.get(0).getPostList());
        model.addAttribute("targetPost", noteList.get(0).getPostList().get(0));

        return "main";
    }

    @GetMapping("/{targetNoteid}")
    public String detail(@PathVariable("targetNoteid") Long targetNoteid, Model model) {
        Note targetNote = noteService.findByNoteId(targetNoteid);

        model.addAttribute("noteList", noteService.getList());
        model.addAttribute("targetNote", targetNote);
        model.addAttribute("postList", targetNote.getPostList());
        model.addAttribute("targetPost", targetNote.getPostList().get(0));

        return "main";
    }

    @PostMapping("/create")
    public String create() {
        Note note = noteService.createNote();
        postService.writePost(note);
        return "redirect:/note/" + getLastNum();
    }

    @PostMapping("/delete")
    public String delete(Long targetNoteid) {
        Note targetNote = noteService.findByNoteId(targetNoteid);
        if (targetNote.getChildList().isEmpty()) {
            if (noteService.getAllNote().size() != 1) {
                noteService.deleteNote(targetNoteid);
            }
        } else {
            if (noteService.getList().size() != 1) {
                noteService.deleteNote(targetNoteid);
            }
        }
        return "redirect:/";
    }

    @PostMapping("/groupAdd")
    public String groupAdd(Long targetNoteid) {
        Note parentNote = noteService.findByNoteId(targetNoteid);
        Note childNote = noteService.createNote();
        postService.writePost(childNote);
        noteService.groupAdd(parentNote, childNote);
        return "redirect:/note/" + getLastNum();
    }

    public Long getLastNum() {
        return noteService.getAllNote().get(noteService.getAllNote().size() - 1).getId();
    }
}
