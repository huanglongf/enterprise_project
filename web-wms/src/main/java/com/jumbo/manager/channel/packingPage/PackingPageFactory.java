package com.jumbo.manager.channel.packingPage;

public interface PackingPageFactory {

    String PACKING_PAGE_ESPRIT = "esprit";
    String PACKING_PAGE_LHLH = "lhlh";
    String PACKING_PAGE_NIKE = "nike";
    String PACKING_PAGE_MICROSOFT = "microsoft";
    String PACKING_PAGE_CONVERSE = "converse";
    String PACKING_PAGE_CACHECACHE = "cachecache";
    String PACKING_PAGE_BONOBO = "bonobo";
    String PACKING_PAGE_GBONOBO = "gbonobo";
    String PACKING_PAGE_JACKWOLFSKIN = "jackwolfskin";
    String PACKING_PAGE_TIGER = "tiger";
    String PACKING_PAGE_AMAZON = "amazon";
    String PACKING_PAGE_GYMBOREE = "gymboree";
    String PACKING_PAGE_BURBERRY = "burberry";
    String PACKING_PAGE_ANGFA = "angfa";
    String PACKING_PAGE_CATHKIDSTON = "cathkidston";
    String PACKING_PAGE_NIKEHK = "nikehk";
    String PACKING_PAGE_MICROSOFTHK = "microsofthk";
    String PACKING_PAGE_ITCHANNEL = "itchannel";
    String PACKING_PAGE_NIKE_O2OQS = "nikeo2oqs";
    String PACKING_PAGE_COLUMBIA = "columbia";
    String PACKING_PAGE_PUMA = "puma";
    String PACKING_PAGE_PUMA1 = "puma1";// 天猫
    String PACKING_PAGE_PUMA1_1 = "puma1_1";// 天猫电子发票
    String PACKING_PAGE_PUMA1_1_1 = "puma1_1_1";// 天猫电子发票改版

    String PACKING_PAGE_PUMA2 = "puma2";// 京东
    String PACKING_PAGE_PUMA2_2 = "puma2_2";// 京东电子发票
    String PACKING_PAGE_PUMA2_2_2 = "puma2_2_2";// 京东电子发票改版

    String PACKING_PAGE_PUMA3 = "puma3";// 官网
    String PACKING_PAGE_PUMA3_3 = "puma3_3";// 官网电子发票
    String PACKING_PAGE_PUMA3_3_3 = "puma3_3_3";// 官网电子发票改版

    String PACKING_PAGE_AMERICANSTANDARD = "americanstandard";
    String PACKING_PAGE_EMPORIOARMANI = "emporioarmani";
    String PACKING_PAGE_STARBUCKS = "starbucks";
    String PACKING_PAGE_CATHKIDSTONHK = "cathkidstonhk";
    String PACKING_PAGE_SAMSUNG = "samsung";
    String PACKING_PAGE_LOCCITANE = "loccitane";
    String PACKING_PAGE_HKNBA = "hknba";
    String PACKING_PAGE_SUNGLASSHUT = "sunglasshut";
    String PACKING_PAGE_RAYBAN = "rayban";
    String PACKING_PAGE_CLOT = "clot";
    String PACKING_PAGE_OAKLEY = "oakley";
    String PACKING_PAGE_ADIDAS = "adidas";
    String PACKING_PAGE_ADIDAS2 = "adidas2";


    String PACKING_PAGE_SPEEDO = "speedo";
    String PACKING_PAGE_CK = "ck";
    String PACKING_PAGE_NIKE_GW = "nikegw";

    String PACKING_PAGE_ROGER_GALLET = "rogerGallet";


    String PACKING_PAGE_MK = "mk";

    String PACKING_PAGE_GO_WILD = "goWild";

    String PACKING_PAGE_CONVERSE_TM = "conversetm";

    String PACKING_PAGE_REEBOK_ONLINE = "reebokonline";
    String PACKING_PAGE_REEBOK_MALL = "reebokmall";

    String PACKING_PAGE_GOPRO = "gopro";

    String PACKING_PAGE_NIKE_PACKING_LIST = "nikePackingList";

    String PACKING_PAGE_GUCCI = "gucci";

    String PACKING_PAGE_RALPH_LAUREN = "rl";

    String PACKING_PAGE_HERSCHEL = "herschel";

    String PACKING_PAGE_THERMAL = "thermal";
    
    String PACKING_PAGE_RALPH_LAUREN_WX = "rlwx";// ralph lauren微信商城装箱单模板


    /**
     * 获取装箱清单类型
     * 
     * @param type
     * @return
     */
    PackingPageInterface getPackingPage(String type);
}
