package com.alct.mdpsdksample.demo;

import com.alct.mdp.model.Goods;

import java.util.ArrayList;
import java.util.List;

public class MockGoods {

    public static List<Goods> init() {

        Goods goods0 = new Goods();
        Goods goods1 = new Goods();
        goods0.setItemNo(1);
        goods0.setGoodsName("玉米");
        goods0.setQuantity(5);
        goods0.setReceivedQuantity(5);
        goods0.setDamageQuantity(0);
        goods0.setLostQuantity(0);
        goods0.setUnit("车");

        goods1.setItemNo(11);
        goods1.setGoodsName("娃娃");
        goods1.setQuantity(5);
        goods1.setReceivedQuantity(5);
        goods1.setDamageQuantity(0);
        goods1.setLostQuantity(0);
        goods1.setUnit("个");

        List<Goods> goodsList = new ArrayList<>();
        goodsList.add(goods0);
        goodsList.add(goods1);

        return goodsList;
    }
}