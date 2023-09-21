package cn.zw.icore.ap.rest;

import cn.zw.icore.ap.base.entity.User;
import cn.zw.icore.ap.base.entity.UserMapper;
import cn.zw.icore.ap.base.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/test")
@Slf4j
public class test {
    @Resource
    UserMapper userMapper;

    @PostMapping
    public Result add(@Valid @RequestBody User user) {
        log.debug("name:{}", user);
        userMapper.insert(user);
        return Result.success(user);
    }

    @GetMapping("/getUser/{id}")
    public Result getUser(@PathVariable String id) {
        log.debug("id:{}", id);
        User user = userMapper.selectById(id);
        return Result.success(user);
    }

}
