package com.even.push.controller;


import com.even.push.dto.RegistDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/push-server/")
public class RegisterController {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 进行注册
     */
    @RequestMapping(value = "unRegist",method = RequestMethod.POST)
    public void unRegist(RegistDto registDto){
        redisTemplate.boundSetOps(registDto.getTopicPre()).add(registDto.getMark());
    }

    /**
     * 取消注册
     * @param registDto
     */
    @RequestMapping(value = "regist",method = RequestMethod.POST)
    public void regist(RegistDto registDto){
        redisTemplate.boundSetOps(registDto.getTopicPre()).remove(registDto.getMark());
    }
}
