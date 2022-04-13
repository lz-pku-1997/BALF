package com.balf.boot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.druid.support.json.JSONUtils;
import com.balf.boot.bean.BlockInfo;
import com.balf.boot.mapper.BlockMapper;
import com.balf.boot.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    BlockMapper blockMapper;

    public BlockInfo getOneBlockByConfidenceDesc(Integer index, String caseID){
        return blockMapper.getOneBlockByConfidenceDesc(index, caseID);
    }


    public Integer setOneAccepted(String imgName, Integer isAccepted,String caseID){
        return blockMapper.setOneAccepted(imgName, isAccepted,caseID);
    }


    public Integer addMessage(BlockInfo blockInfo, String addMessage,String caseID,String userInfo){
        String oldMessageJson = blockInfo.getComment();
        List<ArrayList> oldMessageList = JSON.parseArray(oldMessageJson, ArrayList.class);

        ArrayList aMessage = new ArrayList();
        aMessage.add(addMessage);
        aMessage.add(userInfo);
        aMessage.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//设置日期格式

        if(oldMessageList == null) oldMessageList = new ArrayList();
        oldMessageList.add(aMessage);

        String newMessageJson = JSON.toJSONString(oldMessageList);

        return blockMapper.addMessage(blockInfo, newMessageJson,caseID);
    }



    public List cntRatio(String caseID){
        Map map = blockMapper.cnt(caseID);

        Long t0 = (Long) map.get("t0");
        Long t_small = (Long) map.get("t_small");
        Long t1 = (Long) map.get("t1");
        Long t3 = (Long) map.get("t3");
        Long t4 = (Long) map.get("t4");

        Float sum = Float.valueOf(t0 + t_small + t1 + t3 + t4);


        List res = new ArrayList();

        res.add(String.format("%.2f", t0*100/sum)+"%");
        res.add(String.format("%.2f", t_small*100/sum)+"%");
        res.add(String.format("%.2f", t1*100/sum)+"%");
        res.add(String.format("%.2f", t3*100/sum)+"%");
        res.add(String.format("%.2f", t4*100/sum)+"%");



        return res;

    }


    // 这个应该移动到service里
    public List toNxnList(String nxn, Integer maxL, Integer pad){
        ArrayList<String> strings = new ArrayList<>();
        Integer r = Integer.valueOf(nxn.split("x")[0]);
        Integer c = Integer.valueOf(nxn.split("x")[1]);
        Integer centerIntRow = min(maxL - pad - 1, max(pad, r));
        Integer centerIntCol = min(maxL - pad - 1, max(pad, c));
        for(int i = centerIntRow- pad; i<centerIntRow + pad + 1; i+=1){
            for (int j = centerIntCol- pad; j<centerIntCol + pad + 1; j+=1){
                strings.add(i + "x" + j);
            }
        }

        return strings;
    }

}