package com.example.tourism.biz;

import com.example.tourism.entity.TravelsBean;
import com.example.tourism.utils.CTextUtils;
import com.example.tourism.utils.ImageUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 抓取游记列表信息管理类
 * Name:laodai
 * Time:2019.10.18
 */
public class TravelsDataManger {

    /**
     * 构造方法
     */
    public TravelsDataManger() {
    }

    /**
     * 抓取文章列表信息
     * @param document
     * @return List<TravelsBean>
     */
    public List<TravelsBean> getTravels(Document document) {
        List<TravelsBean> beanList = new ArrayList<>();
        Element element = document.select("ul.b_strategy_list").first();
        Elements elements = element.select("li.list_item");
        for (Element ment : elements) {
            //文章ID
            String travelsId = ment.select("h2.tit").attr("data-bookid");
            //用户主页地址
            String userHref = ment.select("a.face").attr("abs:href");
            //用户头像URL
            String userSrc = ment.select("img.imgf").attr("abs:src");
            //文章内容
            String content = ment.select("a[href]").text();
            //文章详情页地址
            String href = ment.select("a[href]").attr("abs:href");
            //用户名称
            String userName = ment.select("span.user_name").select("a").text();
            //用户别称
            String nickName = ment.select("span.user_name_icon").attr("title");
            //出发日期
            String date = ment.select("span.date").text();
            //天数
            String days = ment.select("span.days").text();
            //照片数量
            String photo_nums = ment.select("span.photo_nums").text();
            //金额（人均）
            String fee = ment.select("span.fee").text();
            //关系
            String people = ment.select("span.people").text();
            //浏览数量
            String browse = CTextUtils.getBrowses(ment.select("span.icon_view").text());
            //点赞数量
            String foubles = CTextUtils.getFoubles(ment.select("span.icon_love").text());
            //评论数量
            String comment = CTextUtils.getComment(ment.select("span.icon_comment").text());
            //途径
            String channel = CTextUtils.getCannelStr(ment.select("p.places").text());
            //行程
            String trips = CTextUtils.getTripStr(ment.select("p.places").text());
            //获取图片集合
            Elements picElements = ment.select("ul.pics").select("li.pic");
            List<String> picUrlList = new ArrayList<>();
            for (Element picMent : picElements) {
                String picUrl = ImageUtils.getCutPicUrl(picMent.select("img").attr("abs:src"));
                picUrlList.add(picUrl);
            }
            //创建游记列表对象
            TravelsBean travelsBean = new TravelsBean();
            //设置数据
            travelsBean.setTravelsId(travelsId);
            travelsBean.setTitle(content);
            travelsBean.setHref(href);
            travelsBean.setUserPicUrl(userSrc);
            travelsBean.setUserHref(userHref);
            travelsBean.setUserName(userName);
            travelsBean.setNickName(nickName);
            travelsBean.setTime(date);
            travelsBean.setDays(days);
            travelsBean.setPhotoNumber(photo_nums);
            travelsBean.setPrice(fee);
            travelsBean.setRelation(people);
            travelsBean.setBrowse(browse);
            travelsBean.setFoubles(foubles);
            travelsBean.setComment(comment);
            travelsBean.setChannel(channel);
            travelsBean.setTrips(trips);
            travelsBean.setPicUrl(picUrlList);
            //添加数据
            beanList.add(travelsBean);
        }
        return beanList;
    }

}
