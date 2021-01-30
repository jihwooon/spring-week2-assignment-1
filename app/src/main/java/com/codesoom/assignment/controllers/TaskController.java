package com.codesoom.assignment.controllers;

import com.codesoom.assignment.Task;
import com.codesoom.assignment.TaskList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Handle requests about task.
 */
@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {
    TaskList taskList = new TaskList();

    @GetMapping
    public List<Task> list() {
        return taskList.getTaskList();
    }

    @GetMapping("/{id:[0-9]+}")
    public Task detail(@PathVariable Long id) {
        return taskList.getTask(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@RequestBody Task task) {
        try {
            return taskList.add(task);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error"
            );
        }
    }

    @RequestMapping(path = "/{id:[0-9]+}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        return taskList.modify(id, task.getTitle());
    }

    @DeleteMapping("/{id:[0-9]+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable Long id) {
        if (taskList.remove(id)) {
            return "Deleted";
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Fail Deleted"
        );
    }
}
