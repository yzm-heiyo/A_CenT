package com.centanet.hk.aplus.common;

/**
 * Created by yangzm4 on 2018/3/22.
 */

public class APSystemParameterType {

    // 參數類型
    public static final int none = 0;       // 默認無
    public static final int estatePhoto = 1;    // 小區圖類型(小區環境、門頭，規劃圖、車庫和大廈圖等）
    public static final int propertyPhoto = 2;   // 室內圖類型(臥室、廚房、客廳、衛生間)
    public static final int propertyUsage = 3;  // 物業用途（住宅、商業）
    public static final int property = 4; // 單位類型|單位類型（開間、平層)
    public static final int propertyFrameWork = 5;// 房屋結構（框架、磚混、鋼混）
    public static final int landGrade = 6; // 土地級別（一級、二級、三級、四級)
    public static final int roomNoRule = 7; // 單位規則（字母、數字任意組合；字母、數字、字符任意組合；樓層(2位)+房間號(2位數字)；）
    public static final int houseDirection = 8; // 座向
    public static final int coordinates = 9; // 經緯度坐標類型（百度，google，業務等）
    public static final int certificate = 10; // 證件類型（身份證，軍官證，護照，駕駛證等）
    public static final int educationPhase = 11; // 教育階段（幼兒園，小學，中學，高中，大學等）
    public static final int educationNature = 12; // 教育機構性質（公立，私立等）
    public static final int educationLevel = 13;// 教育機構級別（市重點，區重點，普通等）\
    public static final int leisureFacility = 14; // 休閑設施類型
    public static final int livingFacility = 15; // 生活設施類型
    public static final int medicalLevel = 16; // 醫療機構級別
    public static final int natureFacility = 17; // 自然設施類型
    public static final int transportation = 18; // 交通設施類型（公交，地鐵，環路，機場，高速，主干道等）
    public static final int trade = 19; // 交易類型(出租、出售)
    public static final int trust = 20; // 委託類型(出租、出售、租售)
    public static final int trustor = 21; // 委託人類型（業主、業主關係人、中介人、客戶、轉租戶）
    public static final int propertySource = 22; // 房源來源（安居客、搜房、新浪、58同城、駐守、上門、電話來訪、其他）
    public static final int gender = 23; // 性別(先生、女士)
    public static final int propertySituation = 24;// 房屋現狀:空房、業主住、租客住、朋友住、中介已收購，壓房等
    public static final int house = 25;// 間隔:一居、兩居、三居、四居、五居、五居以上 Item規則:=1，>5
    public static final int houseFloor = 26;// 樓層:地下、1層、6層以下、6-12層、12層以上、頂層 Item規則:<=0,=1,<6,>=6|<=12,>12,Top
    public static final int propertyTag = 27; // 房源標籤:產權滿五年、業主唯一住房、急售房 Item規則:對應數據庫中字段名
    public static final int propertyContribution = 28;      // 房源貢獻:獨家、上錢委託、鎖匙、實勘、免擾  Item規則:對應數據庫中字段名
    public static final int propertyFollow = 29;      // 房源跟進類型:房源清洗、新增業主電話、申請轉盤、經理點評  Item規則:系統中有枚舉值對應
    public static final int parking = 30;      // 車位類型:無車位、地下車位、地上車位
    public static final int propertySaleReason = 31;      // 源出售原因:置換、急用錢、孩子上學、工作調動、移民、其他
    public static final int propertyRent = 32;      // 租賃方式:整租、合租、短租
    public static final int propertyRentPayment = 33;      // 出租支付類型:押一付三、押一付二、押一付一、半年付、年付、面議
    public static final int propertyRentLimit = 34;      // 出租詳情:限男生、限女生、限夫妻
    public static final int propertyDate = 35;      // 房源查詢日期類型:委託日期、最後跟進日期、登記日期、發布廣告日期
    public static final int electric = 36;      //  家用電器類型:電冰箱、洗衣機、電磁爐、消毒柜、電腦、空調、DVD、電視機、微波爐、熱水器
    public static final int furniture = 37;      // 家具類型:衣柜、書桌、沙發、床、餐桌、椅子、床墊、茶幾
    public static final int decorationSituation = 38;      // 裝修情況:豪裝、精裝、中裝、簡裝、毛坯
    public static final int myPropertyContribution = 39;    // 我的房源貢獻:我的獨家、我的上錢委託、我的鎖匙、我的實勘 Item規則:對應數據庫中字段名
    public static final int seeProperty = 40;    //  看房類型(睇租、睇售、睇租售)
    public static final int customerContact = 41;    // 客戶聯繫人類型(客戶、客戶關係人)
    public static final int province = 42;    // 省份名稱
    public static final int work = 43;   // 工作類型(政府機關、金融證券...)
    public static final int creditStatus = 44;   // 貸款狀態，包含：有貸款、無貸款
    public static final int inquiryTrade = 45;   // 客源交易類型（求購、求租、租購）
    public static final int buyReason = 46;   // 買樓原因（單身居住、結婚用房、改善居住環境、子女上學、工作就近、交通便利、養老、出租、自住兼投資、投資抄賣）
    public static final int emergency = 47;   // 迫切度（一個月內、三個月內、半年內）
    public static final int payCommission = 48;   // 付佣方式（不付佣、半個月、一個月、商議）
    public static final int inquiryPayment = 49;   // 買房付款方式（全款、貸款）
    public static final int inquirySource = 50;  // 客源來源（400電話、外網委託、搜房網、安居客、焦點、58同城、新浪網、趕集網、上門客戶、切戶/駐守、轉接客、其他）
    public static final int inquiryFeature = 51;  // 客戶特征（首次置業者、投資炒家、常年工作流動人士、單人工作丁克家庭、雙人工作丁克家庭）
    public static final int inquiryStatus = 52;  // 客戶狀態（有效、預定、已購、已租、暫緩、無效）
    public static final int propertyRightNature = 53;  // 產權性質（商品房、已購公有住房、向社會公開銷售的經濟適用房、按經濟適用住房管理的房屋、其他房源）
    public static final int searchCriteria = 54;  // 默認個人搜尋條件數量 3個
    public static final int roomType = 55; // 戶型（直門獨、中獨、偏獨、田字格、開間、H戶型、兩室夾廳、刀把偏單、扁擔偏單、開門偏單、假H戶型、中單、雙陽、其他）
    public static final int roomTypePhoto = 56; // 戶型圖
    public static final int inquiryLevel = 57; // 客源等級（A類睇樓3次以上、B類睇樓2次、C類睇樓1次、D類未睇樓）
    public static final int inquiryTag = 58; // 客源標籤（滿5年、唯一）Item規則:對應數據庫中字段名
    public static final int familySize = 59; //  居住人數（單身、夫妻、一家三口、3人以上）
    public static final int inquiryFollow = 60;    // 客源跟進類型:錄入約看 錄入睇樓 意向金 議價談判 過戶 客戶需求 信息補充 轉客 轉為公客 , 電話無效 客戶沒意向 轉為私客  ItemValue規則：1-全部客源，2-公客池,3-全部客源+公客池 FlagDefault規則：1-有業務邏輯，必選項，2-沒有業務邏輯，可選項
    public static final int propertyCalcTag = 61;    // 房源計算標籤:72小時新增房、30天租約到期房   Item規則:對應數據庫中字段名
    public static final int inquiryCalcTag = 62;   // 客源計算標籤:72小時跟進到期客、30天租房到期客 Item規則:對應數據庫中字段名
    public static final int roleType = 63;   // 角色類型: Item規則:對應數據庫中字段名
    public static final int propertyFollowTemplate = 64;   // 房源常用跟進: Item規則:對應數據庫中字段名
    public static final int lookPropertyTime = 65;   // 方便看房時間:時間不限、周一到周五 、周六日、提前預約
    public static final int allPropertyFollow = 66;   // 通盤房源-跟進
    public static final int publicPropertyFollow = 67;   // 公盤池-跟進
    public static final int recommendPropertyFollow = 68;   // 推薦房源-跟進
    public static final int myRecommendPropertyFollow = 69; // 我的推薦-跟進
    public static final int contributePropertyFollow = 70;// 房源貢獻-跟進"
    public static final int favoritePropertyFollow = 71;// 我的收藏-跟進
    public static final int responsiblePropertyFollow = 72;// 我的樓主盤-跟進
    public static final int allInquiryFollow = 73;// 全部客源-跟進
    public static final int publicInquiryFollow = 74; // 公客池-跟進
    public static final int propertyStatusCategory = 75; // 房源狀態分類 1-有效，2-暫緩，3-預定，4-無效
    public static final int channel = 76; // 端口配置
    public static final int regionType = 77; // 中原區域類型
    public static final int estateTagType = 78;// 樓盤標籤類型：地標，大樓盤
    public static final int propertyCard = 79;// 房源產證信息
    public static final int buildingType = 80;  // 大廈類型：住宅，商鋪，寫字樓，車位
    public static final int estateStatus = 81; // 樓盤類型：右手原則小區，實際門牌小區
    public static final int buildingPhotoType = 82;  // 大廈圖類型(門牌、郵箱等）
    public static final int propertyStatus = 83; // 房源狀態 數據存儲：PropertyStatus表
    public static final int performance = 84; // 業績聲明（圈盤、鎖匙、轉介、成交、合作、其他）
    public static final int systemOnOff = 98; // 系統開關
    public static final int externalResource = 99;// 外部資源
    public static final int systemConfig = 100;// 系統配置 房源推薦策略枚舉:ItemNo[10],ItemValue[PropertyRecommendPolicyEnum:1-單一推薦，2-重復推薦]
    public static final int functionModule = 101;// 功能模塊
    public static final int operationItem = 102;  // 操作項
    public static final int operationType = 103;  // 操作類型
    public static final int advertTitle = 105; // 房源廣告標題
    public static final int advertKeyWord = 106;// 房源廣告關鍵字
    public static final int propertyAttribute = 107;  // 房源屬性：主臥朝南、客廳朝南、兩房朝南等
    public static final int proAuditStatus = 108; // 房源審核狀態：虛假房源 真實房源 質疑房源等
    public static final int dwellingType = 109; // 住宅類型：住宅 非住宅
    public static final int dwellingSubType = 110; // 住宅子類型：xxxxx
    public static final int settingFunctionModule = 111; // 業務設置功能模塊
    public static final int settingOperationType = 112; // 業務設置操作類型
    public static final int realSurveyProtectDeadline = 113;// 實勘保護期
    public static final int expertTransaction = 114;  // 行家成交
    public static final int squareSource = 115;  // 面積來源
    public static final int propertyContactType = 116; // 聯系類型
    public static final int propertyNotKeyType = 117; // 無效跟進類型
}
