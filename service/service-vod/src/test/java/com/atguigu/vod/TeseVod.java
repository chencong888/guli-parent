package com.atguigu.vod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.vod.bean.Access;
import com.atguigu.vod.util.AliyunVodSDKUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TeseVod {
    public static void main(String[] args) throws ClientException {
        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(Access.ACCESS_KEY_ID,
                Access.ACCESS_KEY_SECRET);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        request.setVideoId("a0187a5b472146978eab7fd2f64c9cac");

        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        playInfoList.forEach(playInfo -> {
            System.out.println("playInfo = " + playInfo);
        });

        System.out.println("response.getVideoBase().getTitle() = " + response.getVideoBase().getTitle());
        System.out.println("response.getRequestId() = " + response.getRequestId());
    }
}
