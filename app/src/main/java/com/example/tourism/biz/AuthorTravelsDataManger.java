package com.example.tourism.biz;

import com.example.tourism.entity.AuthorTravelsBean;
import com.example.tourism.utils.CTextUtils;
import com.example.tourism.utils.ImageUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class AuthorTravelsDataManger {

    public AuthorTravelsDataManger() {
    }

    public List<AuthorTravelsBean> getAuthorTravelsBeans(Document document) {
        List<AuthorTravelsBean> beanList = new ArrayList<>();
        Element element = document.select("ul.timeline-ct").first();
        Elements elements = element.select("li.item");
        for (Element ment : elements) {
            //获取年份
            String year = ment.select("dl.timeline-date").select("dd").text();
            //获取月、日
            String monthday = ment.select("dl.timeline-date").select("dt").text();
            String releaseDate = year + monthday;
            //获取列表数据
            Element lMnet = ment.select("div.timeline-content").first();
            //标签
            String label = "";
            //文章内容
            String title = "";
            //文章链接地址
            String href = "";
            //文章图片地址
            String picsUrl = "";
            //游记出行日期
            String tripDate = "";
            //出游天数
            String days = "";
            //发表图片数
            String picNums = "";
            //浏览
            String browse = "";
            //点赞数量
            String foubles = "";
            //评论
            String comment = "";
            if (lMnet != null) {
                label = lMnet.select("dt.label").text();
                title = lMnet.select("dd.title").select("a").text();
                href = lMnet.select("dd.title").select("a").attr("abs:href");
                picsUrl = ImageUtils.getCutPicUrl(lMnet.select("dd.pics").select("a[target]").select("img").attr("data-original"));
                tripDate = CTextUtils.getIncomingStringSplits(lMnet.select("dd.clrfix").select("div.cdate").text(), 0);
                days = CTextUtils.getIncomingStringSplits(lMnet.select("dd.clrfix").select("div.cdate").text(), 1);
                picNums = CTextUtils.getIncomingStringSplits(lMnet.select("dd.clrfix").select("div.cdate").text(), 2);
                browse = lMnet.select("div.stat").select("ul.clrfix").select("li.pv").text();
                foubles = lMnet.select("div.stat").select("ul.clrfix").select("li.zan").text();
                comment = lMnet.select("div.stat").select("ul.clrfix").select("li.comment").text();
            }
            AuthorTravelsBean authorTravelsBean = new AuthorTravelsBean();
            authorTravelsBean.setReleaseDate(releaseDate);
            authorTravelsBean.setLabel(label);
            authorTravelsBean.setTitle(title);
            authorTravelsBean.setHref(href);
            authorTravelsBean.setPicsUrl(picsUrl);
            authorTravelsBean.setTripDate(tripDate);
            authorTravelsBean.setDays(days);
            authorTravelsBean.setPicNums(picNums);
            authorTravelsBean.setBrowse(browse);
            authorTravelsBean.setFoubles(foubles);
            authorTravelsBean.setComment(comment);
            beanList.add(authorTravelsBean);
        }
        return beanList;
    }

}
