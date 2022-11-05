package com.ry.controller;

import com.ry.domain.ResponseResult;
import com.ry.domain.dto.AddLinkDto;
import com.ry.domain.dto.LinkListDto;
import com.ry.domain.dto.UpdateLinkDto;
import com.ry.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult pageLinkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto){
        return linkService.pageLinkList(pageNum,pageSize,linkListDto);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody AddLinkDto addLinkDto){
        return linkService.addLink(addLinkDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable("id") Long id){
        return linkService.getLinkById(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody UpdateLinkDto updateLinkDto){
        return linkService.updateLink(updateLinkDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        return linkService.deleteLink(id);
    }

    @DeleteMapping("/batchRemove")
    public ResponseResult removeLinks(@RequestBody List<Long> ids){
        return linkService.removeLinks(ids);
    }
}
