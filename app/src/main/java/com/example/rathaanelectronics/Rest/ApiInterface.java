package com.example.rathaanelectronics.Rest;

import static com.example.rathaanelectronics.Rest.ApiConstants.ADD_ADDRESS;
import static com.example.rathaanelectronics.Rest.ApiConstants.ADD_TO_CART;
import static com.example.rathaanelectronics.Rest.ApiConstants.ADD_TO_COMPARE;
import static com.example.rathaanelectronics.Rest.ApiConstants.ADD_TO_WISH_LIST;
import static com.example.rathaanelectronics.Rest.ApiConstants.ALLCATEGORIES;
import static com.example.rathaanelectronics.Rest.ApiConstants.BESTDEALS;
import static com.example.rathaanelectronics.Rest.ApiConstants.CAT_PRODUCT;
import static com.example.rathaanelectronics.Rest.ApiConstants.CHANGE_CART_QUANTITY;
import static com.example.rathaanelectronics.Rest.ApiConstants.DEALS_AND_OFFERS;
import static com.example.rathaanelectronics.Rest.ApiConstants.DELETE_CART_ITEM;
import static com.example.rathaanelectronics.Rest.ApiConstants.DELETE_WISH_LIST;
import static com.example.rathaanelectronics.Rest.ApiConstants.Forgot;
import static com.example.rathaanelectronics.Rest.ApiConstants.GUEST_TOKEN;
import static com.example.rathaanelectronics.Rest.ApiConstants.HOME_BEST_DEALS_CATEGORIES;
import static com.example.rathaanelectronics.Rest.ApiConstants.HOME_BEST_DEALS_CATEGORY_WISE;
import static com.example.rathaanelectronics.Rest.ApiConstants.HOME_NON_TIMER_OFFER;
import static com.example.rathaanelectronics.Rest.ApiConstants.HOME_OFFER_BANNER;
import static com.example.rathaanelectronics.Rest.ApiConstants.HOME_TIMER_OFFER;
import static com.example.rathaanelectronics.Rest.ApiConstants.HOTDEALS;
import static com.example.rathaanelectronics.Rest.ApiConstants.LIST_BUNDLE_PRODUCTS;
import static com.example.rathaanelectronics.Rest.ApiConstants.LIST_COMPARE;
import static com.example.rathaanelectronics.Rest.ApiConstants.NEWARRIVALS;
import static com.example.rathaanelectronics.Rest.ApiConstants.PICKUPSTORELIST;
import static com.example.rathaanelectronics.Rest.ApiConstants.PROD_DETAILS;
import static com.example.rathaanelectronics.Rest.ApiConstants.REMOVE_COMPARE;
import static com.example.rathaanelectronics.Rest.ApiConstants.SEARCHPRODUCT;
import static com.example.rathaanelectronics.Rest.ApiConstants.SHOWWISHLIST;
import static com.example.rathaanelectronics.Rest.ApiConstants.SHOW_ADDRESS;
import static com.example.rathaanelectronics.Rest.ApiConstants.SLIDER;
import static com.example.rathaanelectronics.Rest.ApiConstants.TOPTWENTY;
import static com.example.rathaanelectronics.Rest.ApiConstants.VIEW_CART;
import static com.example.rathaanelectronics.Rest.ApiConstants.WISH_LIST;
import static com.example.rathaanelectronics.Rest.ApiConstants.signin;
import static com.example.rathaanelectronics.Rest.ApiConstants.signup;

import com.example.rathaanelectronics.Model.AddToCompareResponseModel;
import com.example.rathaanelectronics.Model.AddressResponseModel;
import com.example.rathaanelectronics.Model.AllCategoriesModel;
import com.example.rathaanelectronics.Model.BestDealCategory;
import com.example.rathaanelectronics.Model.BestDealModel;
import com.example.rathaanelectronics.Model.BundleProductModel;
import com.example.rathaanelectronics.Model.CartResponseModel;
import com.example.rathaanelectronics.Model.CategoryProductModel;
import com.example.rathaanelectronics.Model.CommonResponseModel;
import com.example.rathaanelectronics.Model.CompareListModel;
import com.example.rathaanelectronics.Model.DealsModel;
import com.example.rathaanelectronics.Model.ForgotPasswordModel;
import com.example.rathaanelectronics.Model.GuestTokenModel;
import com.example.rathaanelectronics.Model.HomeOfferBannerModel;
import com.example.rathaanelectronics.Model.NewArrivalModel;
import com.example.rathaanelectronics.Model.PickUpStoreModel;
import com.example.rathaanelectronics.Model.ProductDetailsModel;
import com.example.rathaanelectronics.Model.SearchProductModel;
import com.example.rathaanelectronics.Model.ShowCartResponseModel;
import com.example.rathaanelectronics.Model.SignInModel;
import com.example.rathaanelectronics.Model.SignUpModel;
import com.example.rathaanelectronics.Model.SliderModel;
import com.example.rathaanelectronics.Model.TimerOfferModel;
import com.example.rathaanelectronics.Model.WishListResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiInterface {

//signup

    @FormUrlEncoded
    @POST(signup)
    Call<SignUpModel> signup(@Header("LG-APP-KEY") String auth,
                             @Field("fristname") String fristname,
                             @Field("lastname") String lastname,
                             @Field("usermail") String usermail,
                             @Field("dob") String dob,
                             @Field("gender") String gender,
                             @Field("userphone") String userphone,
                             @Field("userlevel") String userlevel,
                             @Field("createpass") String createpass,
                             @Field("confirmpass") String confirmpass);

    @FormUrlEncoded
    @POST(signin)
    Call<SignInModel> signin(@Header("LG-APP-KEY") String auth,
                             @Field("username") String username,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST(Forgot)
    Call<ForgotPasswordModel> ForgotPassword(@Header("LG-APP-KEY") String auth,
                                             @Field("mailid") String Email);


    @POST(SLIDER)
    Call<SliderModel> Slider(@Header("LG-APP-KEY") String auth);

    @POST(TOPTWENTY)
    Call<DealsModel> TopTwenty(@Header("LG-APP-KEY") String auth,
                               @Header("Authorization") String token);


    @POST(HOTDEALS)
    Call<DealsModel> HotDeals(@Header("LG-APP-KEY") String auth,
                              @Header("Authorization") String token);

    @POST(NEWARRIVALS)
    Call<NewArrivalModel> NewArrivals(@Header("LG-APP-KEY") String auth,
                                      @Header("Authorization") String token);


    @POST(BESTDEALS)
    Call<BestDealModel> BestDeals(@Header("LG-APP-KEY") String auth);


    @POST(PICKUPSTORELIST)
    Call<PickUpStoreModel> PickUpStore(@Header("LG-APP-KEY") String auth);

    @POST(ALLCATEGORIES)
    Call<AllCategoriesModel> AllCategories(@Header("LG-APP-KEY") String auth);

    @FormUrlEncoded
    @POST(SEARCHPRODUCT)
    Call<SearchProductModel> searchProducts(@Header("LG-APP-KEY") String auth,
                                            @Field("search") String username,
                                            @Field("selectcat") String password);

    @FormUrlEncoded
    @POST(ADD_TO_CART)
    Call<CartResponseModel> addToCart(@Header("LG-APP-KEY") String auth,
                                      @Header("Authorization") String token,
                                      @Field("pid") String productId,
                                      @Field("qty") String Quantity);

    @POST(VIEW_CART)
    Call<ShowCartResponseModel> viewCart(@Header("LG-APP-KEY") String auth,
                                         @Header("Authorization") String token);

    @POST(WISH_LIST)
    Call<WishListResponseModel> showWishlist(@Header("LG-APP-KEY") String auth,
                                             @Header("Authorization") String token);

    @FormUrlEncoded
    @POST(DELETE_WISH_LIST)
    Call<CommonResponseModel> deleteWishList(@Header("LG-APP-KEY") String auth,
                                             @Header("Authorization") String token,
                                             @Field("product_id") String productId);
    @FormUrlEncoded
    @POST(DELETE_CART_ITEM)
    Call<CommonResponseModel> deleteCartItem(@Header("LG-APP-KEY") String auth,
                                      @Header("Authorization") String token,
                                      @Field("cid") String cartId);
    @FormUrlEncoded
    @POST(CHANGE_CART_QUANTITY)
    Call<CommonResponseModel> changeCartQuantity(@Header("LG-APP-KEY") String auth,
                                             @Header("Authorization") String token,
                                             @Field("cid") String cartId,
                                             @Field("qty") String quantity);

    @FormUrlEncoded
    @POST(ADD_TO_WISH_LIST)
    Call<CommonResponseModel> addToWishList(@Header("LG-APP-KEY") String auth,
                                                 @Header("Authorization") String token,
                                                 @Field("product_id") String productId);
    @FormUrlEncoded
    @POST(PROD_DETAILS)
    Call<ProductDetailsModel> getProductDetails(@Header("LG-APP-KEY") String auth,
                                                @Header("Authorization") String token,
                                                @Field("pid") String productId);
    @FormUrlEncoded
    @POST(GUEST_TOKEN)
    Call<GuestTokenModel> getGuestToken(@Header("LG-APP-KEY") String auth,
                                        @Field("device_token") String deviceId);

    @FormUrlEncoded
    @POST(ADD_TO_COMPARE)
    Call<AddToCompareResponseModel> addToCompare(@Header("LG-APP-KEY") String auth,
                                                 @Header("Authorization") String token,
                                                 @Field("product_id") String productId);

    @FormUrlEncoded
    @POST(REMOVE_COMPARE)
    Call<AddToCompareResponseModel> removeCompare(@Header("LG-APP-KEY") String auth,
                                                 @Header("Authorization") String token,
                                                 @Field("compare_id") String compareId);

    @POST(LIST_COMPARE)
    Call<CompareListModel> listCompare(@Header("LG-APP-KEY") String auth,
                                         @Header("Authorization") String token);

    @FormUrlEncoded
    @POST(ADD_ADDRESS)
    Call<CommonResponseModel> addAddress(@Header("LG-APP-KEY") String auth,
                                                 @Header("Authorization") String token,
                                                 @Field("fname") String firstName,
                                                 @Field("lname") String lastName,
                                                 @Field("adrtitle") String addrTitle,
                                                 @Field("mail") String mail,
                                                 @Field("mobile1") String mobileNumber,
                                                 @Field("city") String city,
                                                 @Field("mobile2") String secondMobileNumber,
                                                 @Field("gover") String gover,
                                                 @Field("block") String block,
                                                 @Field("street") String street,
                                                 @Field("avenue") String avenue,
                                                 @Field("hbno") String hbno);

    @POST(SHOW_ADDRESS)
    Call<AddressResponseModel> showAddress(@Header("LG-APP-KEY") String auth,
                                           @Header("Authorization") String token);

//    @GET("Category/all_category")
//    Call<MainCategory> get_category(@Header("X-API-KEY") String auth);

    @FormUrlEncoded
    @POST(CAT_PRODUCT)
    Call<CategoryProductModel> categoryProducts(@Header("LG-APP-KEY") String auth,
                                                @Header("Authorization") String token,
                                                @Field("category_id") String id);

    @POST(HOME_OFFER_BANNER)
    Call<HomeOfferBannerModel> homeOfferBanner(@Header("LG-APP-KEY") String auth);


    @POST(DEALS_AND_OFFERS)
    Call<BestDealModel> dealsAndOffers(@Header("LG-APP-KEY") String auth,
                                       @Header("Authorization") String token);


    @POST(LIST_BUNDLE_PRODUCTS)
    Call<BundleProductModel> listBundleProducts(@Header("LG-APP-KEY") String auth,
                                            @Header("Authorization") String token);


    @POST(HOME_TIMER_OFFER)
    Call<TimerOfferModel> timerOffers(@Header("LG-APP-KEY") String auth,
                                      @Header("Authorization") String token);


    @POST(HOME_NON_TIMER_OFFER)
    Call<TimerOfferModel> nonTimerOffers(@Header("LG-APP-KEY") String auth,
                                                @Header("Authorization") String token);

    @POST(HOME_BEST_DEALS_CATEGORIES)
    Call<BestDealCategory> getBestDealCategories(@Header("LG-APP-KEY") String auth,
                                          @Header("Authorization") String token);
    @FormUrlEncoded
    @POST(HOME_BEST_DEALS_CATEGORY_WISE)
    Call<BestDealModel> BestDealsCatWise(@Header("LG-APP-KEY") String auth,
                                         @Header("Authorization") String token,
                                         @Field("category_id") String categoryId);
    @POST(HOME_BEST_DEALS_CATEGORIES)
    Call<BestDealCategory> getDealsOffers(@Header("LG-APP-KEY") String auth,
                                                 @Header("Authorization") String token);

//    @FormUrlEncoded
//    @POST("Users/addtocartbundle")
//    Call<AddToCartBundleModel> addToCartBundle(@Header("LG-APP-KEY") String auth,
//                                                 @Header("Authorization") String token,
//                                                 @Field("pid") String pid,
//                                                 @Field("qty") String quantity);
//    @FormUrlEncoded
//    @POST("Users/changecartqty")
//    Call<ChangeCartQtyModel> changeCartQty(@Header("LG-APP-KEY") String auth,
//                                               @Header("Authorization") String token,
//                                               @Field("cid") String cid,
//                                               @Field("qty") String quantity);
//    @FormUrlEncoded
//    @POST("Users/removecartitem")
//    Call<RemoveCartItemModel> removeCartItem(@Header("LG-APP-KEY") String auth,
//                                           @Header("Authorization") String token,
//                                           @Field("cid") String cid);
//    @FormUrlEncoded
//    @POST("Users/wishlistadding")
//    Call<WishlistAddingModel> whishlistAdding(@Header("LG-APP-KEY") String auth,
//                                             @Header("Authorization") String token,
//                                             @Field("product_id") String product_id);
//    @FormUrlEncoded
//    @POST("Users/deletewishlistitem")
//    Call<WishlistDeleteModel> whishlistDelete(@Header("LG-APP-KEY") String auth,
//                                              @Header("Authorization") String token,
//                                              @Field("product_id") String product_id);
//    @FormUrlEncoded
//    @POST("Users/updateadrs")
//    Call<UpdateAddressModel> updateAddressModel(@Header("LG-APP-KEY") String auth,
//                                              @Header("Authorization") String token,
//                                                @Field("fname") String firstName,
//                                                @Field("lname") String lastName,
//                                                @Field("adrtitle") String addrTitle,
//                                                @Field("mail") String mail,
//                                                @Field("mobile1") String mobileNumber,
//                                                @Field("city") String city,
//                                                @Field("mobile2") String secondMobileNumber,
//                                                @Field("gover") String gover,
//                                                @Field("block") String block,
//                                                @Field("street") String street,
//                                                @Field("avenue") String avenue,
//                                                @Field("hbno") String hbno);
//    @FormUrlEncoded
//    @POST("Users/addresssave")
//    Call<AddressSaveModel> addressSaveModel(@Header("LG-APP-KEY") String auth,
//                                                @Header("Authorization") String token,
//                                                @Field("fname") String firstName,
//                                                @Field("lname") String lastName,
//                                                @Field("adrtitle") String addrTitle,
//                                                @Field("mail") String mail,
//                                                @Field("mobile1") String mobileNumber,
//                                                @Field("city") String city,
//                                                @Field("mobile2") String secondMobileNumber,
//                                                @Field("gover") String gover,
//                                                @Field("block") String block,
//                                                @Field("street") String street,
//                                                @Field("avenue") String avenue,
//                                                @Field("hbno") String hbno);
//
//    @POST("Users/addresslist")
//    Call<AddressListModel> addressList(@Header("LG-APP-KEY") String auth,
//                                      @Header("Authorization") String token);
//
//    @POST("Users/wishlistshow")
//    Call<WishistShowModel> wishlistShow(@Header("LG-APP-KEY") String auth,
//                                       @Header("Authorization") String token);
//
//    @POST("Rathaan/storelist")
//    Call<StoreListModel> storeList(@Header("LG-APP-KEY") String auth,
//                                        @Header("Authorization") String token);
//
//    @POST("Users/orderhistorylist")
//    Call<OrderHistoryListModel> orderHistoryList(@Header("LG-APP-KEY") String auth,
//                                   @Header("Authorization") String token);
//    @FormUrlEncoded
//    @POST("Users/orderdetails")
//    Call<OrderDetailsModel> orderDetails(@Header("LG-APP-KEY") String auth,
//                                              @Header("Authorization") String token,
//                                              @Field("orderid") String orderid);
//    @FormUrlEncoded
//    @POST("Users/couponapply")
//    Call<CouponApplyModel> couponApply(@Header("LG-APP-KEY") String auth,
//                                               @Header("Authorization") String token,
//                                               @Field("copuonappling") String copuonappling,
//                                               @Field("carttotal") String carttotal);
//
//    @POST("Users/loyaltypoints")
//    Call<LoyalityPointsModel> loyalityPoints(@Header("LG-APP-KEY") String auth,
//                                                 @Header("Authorization") String token);
//
//    @POST("Users/wallet")
//    Call<WalletModel> wallets(@Header("LG-APP-KEY") String auth,
//                                             @Header("Authorization") String token);
//
//    @POST("Users/walletandloyaltyforcheckout")
//    Call<WalletAndLoyaltyForCheckoutModel> walletAndLoyalityForCheckout(@Header("LG-APP-KEY") String auth,
//                              @Header("Authorization") String token);
//
//    @POST("Users/orderreturnreasons")
//    Call<OrderReturnReasonModel> orderreturnReason(@Header("LG-APP-KEY") String auth,
//                                                                        @Header("Authorization") String token);
//    @FormUrlEncoded
//    @POST("Users/orderreturnrequest")
//    Call<OrderReturnRequestModel> orderreturnRequest(@Header("LG-APP-KEY") String auth,
//                                       @Header("Authorization") String token,
//                                       @Field("order_unique_id") String order_unique_id,
//                                       @Field("pid") String pid,
//                                                     @Field("return_reason") String return_reason,
//                                                     @Field("return_reasontext") String return_reasontext);
//    @FormUrlEncoded
//    @POST("Users/delivery_seting")
//    Call<DeliverySettingsModel> deliverySettings(@Header("LG-APP-KEY") String auth,
//                                         @Header("Authorization") String token,
//                                         @Field("area") String area);
//    @FormUrlEncoded
//    @POST("Users/setdelcharg")
//    Call<SetDeliveryChargeModel> setDeliveryCharge(@Header("LG-APP-KEY") String auth,
//                                                 @Header("Authorization") String token,
//                                                 @Field("area") String area,
//                                                   @Field("gtotal") String gtotal);


}

