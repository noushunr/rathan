package com.example.rathaanelectronics.Data;

import static com.example.rathaanelectronics.Data.ApiConstants.ADD_ADDRESS;
import static com.example.rathaanelectronics.Data.ApiConstants.ADD_TO_CART;
import static com.example.rathaanelectronics.Data.ApiConstants.ADD_TO_COMPARE;
import static com.example.rathaanelectronics.Data.ApiConstants.ADD_TO_WISH_LIST;
import static com.example.rathaanelectronics.Data.ApiConstants.ALLCATEGORIES;
import static com.example.rathaanelectronics.Data.ApiConstants.ALL_AREA_LIST;
import static com.example.rathaanelectronics.Data.ApiConstants.APPLY_COUPON;
import static com.example.rathaanelectronics.Data.ApiConstants.AREA_LIST;
import static com.example.rathaanelectronics.Data.ApiConstants.BESTDEALS;
import static com.example.rathaanelectronics.Data.ApiConstants.BUNDLE_ADD_TO_CART;
import static com.example.rathaanelectronics.Data.ApiConstants.BUNDLE_ADD_TO_WISH_LIST;
import static com.example.rathaanelectronics.Data.ApiConstants.BUNDLE_DELETE_WISH_LIST;
import static com.example.rathaanelectronics.Data.ApiConstants.BUNDLE_DETAILS;
import static com.example.rathaanelectronics.Data.ApiConstants.CART_COUNT;
import static com.example.rathaanelectronics.Data.ApiConstants.CAT_PRODUCT;
import static com.example.rathaanelectronics.Data.ApiConstants.CHANGE_CART_QUANTITY;
import static com.example.rathaanelectronics.Data.ApiConstants.CHECKOUT_AVAILABILITY_CHECK;
import static com.example.rathaanelectronics.Data.ApiConstants.CHECK_OUT;
import static com.example.rathaanelectronics.Data.ApiConstants.CITY_GOVERNARATE;
import static com.example.rathaanelectronics.Data.ApiConstants.CONTACT_MAIL_SEND;
import static com.example.rathaanelectronics.Data.ApiConstants.DEALS_AND_OFFERS;
import static com.example.rathaanelectronics.Data.ApiConstants.DELETE_ADDRESS;
import static com.example.rathaanelectronics.Data.ApiConstants.DELETE_CART_ITEM;
import static com.example.rathaanelectronics.Data.ApiConstants.DELETE_WISH_LIST;
import static com.example.rathaanelectronics.Data.ApiConstants.DEL_AVAILABILITY_CHECK;
import static com.example.rathaanelectronics.Data.ApiConstants.DEL_CHARGE;
import static com.example.rathaanelectronics.Data.ApiConstants.DEL_CHECK;
import static com.example.rathaanelectronics.Data.ApiConstants.DEL_DAYS_AVAILABILITY_CHECK;
import static com.example.rathaanelectronics.Data.ApiConstants.Forgot;
import static com.example.rathaanelectronics.Data.ApiConstants.GOVERNARATE_LIST;
import static com.example.rathaanelectronics.Data.ApiConstants.GUEST_TOKEN;
import static com.example.rathaanelectronics.Data.ApiConstants.HOME_BEST_DEALS_CATEGORIES;
import static com.example.rathaanelectronics.Data.ApiConstants.HOME_BEST_DEALS_CATEGORY_WISE;
import static com.example.rathaanelectronics.Data.ApiConstants.HOME_NON_TIMER_OFFER;
import static com.example.rathaanelectronics.Data.ApiConstants.HOME_OFFER_BANNER;
import static com.example.rathaanelectronics.Data.ApiConstants.HOME_TIMER_OFFER;
import static com.example.rathaanelectronics.Data.ApiConstants.HOTDEALS;
import static com.example.rathaanelectronics.Data.ApiConstants.LIST_BUNDLE_PRODUCTS;
import static com.example.rathaanelectronics.Data.ApiConstants.LIST_COMPARE;
import static com.example.rathaanelectronics.Data.ApiConstants.LOYALTY_APPLIED;
import static com.example.rathaanelectronics.Data.ApiConstants.LOYALTY_POINTS;
import static com.example.rathaanelectronics.Data.ApiConstants.NEWARRIVALS;
import static com.example.rathaanelectronics.Data.ApiConstants.NOTIFICATIONS;
import static com.example.rathaanelectronics.Data.ApiConstants.ORDER_DETAILS;
import static com.example.rathaanelectronics.Data.ApiConstants.ORDER_HISTORY;
import static com.example.rathaanelectronics.Data.ApiConstants.PERSONAL_INFO;
import static com.example.rathaanelectronics.Data.ApiConstants.PERSONAL_INFO_EDIT;
import static com.example.rathaanelectronics.Data.ApiConstants.PICKUPSTORELIST;
import static com.example.rathaanelectronics.Data.ApiConstants.POINTS;
import static com.example.rathaanelectronics.Data.ApiConstants.PROD_DETAILS;
import static com.example.rathaanelectronics.Data.ApiConstants.REMOVE_COMPARE;
import static com.example.rathaanelectronics.Data.ApiConstants.RETURN_POLICY;
import static com.example.rathaanelectronics.Data.ApiConstants.RETURN_POLICY_MAIL;
import static com.example.rathaanelectronics.Data.ApiConstants.RETURN_REASONS;
import static com.example.rathaanelectronics.Data.ApiConstants.RETURN_REQUEST;
import static com.example.rathaanelectronics.Data.ApiConstants.SEARCHPRODUCT;
import static com.example.rathaanelectronics.Data.ApiConstants.SHOW_ADDRESS;
import static com.example.rathaanelectronics.Data.ApiConstants.SLIDER;
import static com.example.rathaanelectronics.Data.ApiConstants.SUB_CAT_PRODUCT;
import static com.example.rathaanelectronics.Data.ApiConstants.TERMS_CONDITIONS;
import static com.example.rathaanelectronics.Data.ApiConstants.TOPTWENTY;
import static com.example.rathaanelectronics.Data.ApiConstants.UPDATE_ADDRESS;
import static com.example.rathaanelectronics.Data.ApiConstants.VIEW_CART;
import static com.example.rathaanelectronics.Data.ApiConstants.WALLET;
import static com.example.rathaanelectronics.Data.ApiConstants.WALLET_APPLIED;
import static com.example.rathaanelectronics.Data.ApiConstants.WISH_LIST;
import static com.example.rathaanelectronics.Data.ApiConstants.signin;
import static com.example.rathaanelectronics.Data.ApiConstants.signup;

import com.example.rathaanelectronics.Model.AddToCompareResponseModel;
import com.example.rathaanelectronics.Model.AddressResponseModel;
import com.example.rathaanelectronics.Model.AllCategoriesModel;
import com.example.rathaanelectronics.Model.AreaModel;
import com.example.rathaanelectronics.Model.BestDealCategory;
import com.example.rathaanelectronics.Model.BestDealModel;
import com.example.rathaanelectronics.Model.BundleDetailModel;
import com.example.rathaanelectronics.Model.BundleProductModel;
import com.example.rathaanelectronics.Model.CartCountModel;
import com.example.rathaanelectronics.Model.CartResponseModel;
import com.example.rathaanelectronics.Model.CategoryProductModel;
import com.example.rathaanelectronics.Model.CheckoutCheckModel;
import com.example.rathaanelectronics.Model.CoinsAppliedModel;
import com.example.rathaanelectronics.Model.CommonResponseModel;
import com.example.rathaanelectronics.Model.CompareListModel;
import com.example.rathaanelectronics.Model.CouponApplyModel;
import com.example.rathaanelectronics.Model.DealsModel;
import com.example.rathaanelectronics.Model.DeliveryChargeModel;
import com.example.rathaanelectronics.Model.DeliveryDateCheckModel;
import com.example.rathaanelectronics.Model.DeliveryDaysCheckModel;
import com.example.rathaanelectronics.Model.ForgotPasswordModel;
import com.example.rathaanelectronics.Model.GovernarateModel;
import com.example.rathaanelectronics.Model.GuestTokenModel;
import com.example.rathaanelectronics.Model.HomeOfferBannerModel;
import com.example.rathaanelectronics.Model.LoyaltyModel;
import com.example.rathaanelectronics.Model.NotificationModel;
import com.example.rathaanelectronics.Model.OrderCancelReasons;
import com.example.rathaanelectronics.Model.OrderDetailsModel;
import com.example.rathaanelectronics.Model.OrderListModel;
import com.example.rathaanelectronics.Model.PickUpStoreModel;
import com.example.rathaanelectronics.Model.PointsModel;
import com.example.rathaanelectronics.Model.PreCheckoutSuccessModel;
import com.example.rathaanelectronics.Model.ProductDetailsModel;
import com.example.rathaanelectronics.Model.ProfileModel;
import com.example.rathaanelectronics.Model.ReturnPolicyModel;
import com.example.rathaanelectronics.Model.SearchProductModel;
import com.example.rathaanelectronics.Model.ShowCartResponseModel;
import com.example.rathaanelectronics.Model.SignInModel;
import com.example.rathaanelectronics.Model.SignUpModel;
import com.example.rathaanelectronics.Model.SliderModel;
import com.example.rathaanelectronics.Model.SubCategoryProducts;
import com.example.rathaanelectronics.Model.TermsContentModel;
import com.example.rathaanelectronics.Model.TimerOfferModel;
import com.example.rathaanelectronics.Model.WalletModel;
import com.example.rathaanelectronics.Model.WishListResponseModel;
import com.example.rathaanelectronics.Model.WishlistModel;

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
    @POST(PERSONAL_INFO_EDIT)
    Call<CommonResponseModel> editProfile(@Header("LG-APP-KEY") String auth,
                                          @Header("Authorization") String token,
                                          @Field("firstname") String fristname,
                                          @Field("lastname") String lastname,
                                          @Field("displayname") String displayName,
                                          @Field("email") String usermail,
                                          @Field("newpassword") String createpass,
                                          @Field("confirmpassword") String confirmpass,
                                          @Field("curpassword") String curPassword);

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
    Call<DealsModel> NewArrivals(@Header("LG-APP-KEY") String auth,
                                 @Header("Authorization") String token);


    @POST(BESTDEALS)
    Call<BestDealModel> BestDeals(@Header("LG-APP-KEY") String auth,
                                  @Header("Authorization") String token);


    @POST(PICKUPSTORELIST)
    Call<PickUpStoreModel> PickUpStore(@Header("LG-APP-KEY") String auth);

    @POST(ALLCATEGORIES)
    Call<AllCategoriesModel> AllCategories(@Header("LG-APP-KEY") String auth);

    @FormUrlEncoded
    @POST(SEARCHPRODUCT)
    Call<SearchProductModel> searchProducts(@Header("LG-APP-KEY") String auth,
                                            @Header("Authorization") String token,
                                            @Field("search") String username,
                                            @Field("selectcat") String password);

    @FormUrlEncoded
    @POST(ADD_TO_CART)
    Call<CartResponseModel> addToCart(@Header("LG-APP-KEY") String auth,
                                      @Header("Authorization") String token,
                                      @Field("pid") String productId,
                                      @Field("qty") String Quantity);

    @FormUrlEncoded
    @POST(BUNDLE_ADD_TO_CART)
    Call<CartResponseModel> bundleAddToCart(@Header("LG-APP-KEY") String auth,
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
    Call<WishlistModel> addToWishList(@Header("LG-APP-KEY") String auth,
                                      @Header("Authorization") String token,
                                      @Field("product_id") String productId);

    @FormUrlEncoded
    @POST(DELETE_WISH_LIST)
    Call<CommonResponseModel> deleteWishList(@Header("LG-APP-KEY") String auth,
                                             @Header("Authorization") String token,
                                             @Field("product_id") String productId);

    @FormUrlEncoded
    @POST(BUNDLE_ADD_TO_WISH_LIST)
    Call<CommonResponseModel> bundleAdToWishList(@Header("LG-APP-KEY") String auth,
                                                 @Header("Authorization") String token,
                                                 @Field("offer_id") String productId);

    @FormUrlEncoded
    @POST(BUNDLE_DELETE_WISH_LIST)
    Call<CommonResponseModel> bundleDeleteWishList(@Header("LG-APP-KEY") String auth,
                                                   @Header("Authorization") String token,
                                                   @Field("offer_id") String productId);

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

    @FormUrlEncoded
    @POST(UPDATE_ADDRESS)
    Call<CommonResponseModel> updateAddress(@Header("LG-APP-KEY") String auth,
                                            @Header("Authorization") String token,
                                            @Field("adrid") String addressId,
                                            @Field("area") String city,
                                            @Field("mobile1") String mobileNumber,
                                            @Field("mobile2") String secondMobileNumber,
                                            @Field("adrtitle") String addrTitle,
                                            @Field("fname") String firstName,
                                            @Field("lname") String lastName,
                                            @Field("gover") String gover,
                                            @Field("block") String block,
                                            @Field("street") String street,
                                            @Field("avenue") String avenue,
                                            @Field("hbno") String hbno,
                                            @Field("mail") String mail

    );

    @POST(SHOW_ADDRESS)
    Call<AddressResponseModel> showAddress(@Header("LG-APP-KEY") String auth,
                                           @Header("Authorization") String token);

    @FormUrlEncoded
    @POST(DELETE_ADDRESS)
    Call<CommonResponseModel> deleteAddress(@Header("LG-APP-KEY") String auth,
                                            @Header("Authorization") String token,
                                            @Field("address_id") String addressId);

//    @GET("Category/all_category")
//    Call<MainCategory> get_category(@Header("X-API-KEY") String auth);

    @FormUrlEncoded
    @POST(CAT_PRODUCT)
    Call<CategoryProductModel> categoryProducts(@Header("LG-APP-KEY") String auth,
                                                @Header("Authorization") String token,
                                                @Field("category_id") String id);

    @FormUrlEncoded
    @POST(APPLY_COUPON)
    Call<CouponApplyModel> applyCoupon(@Header("LG-APP-KEY") String auth,
                                       @Header("Authorization") String token,
                                       @Field("copuonappling") String couponCode,
                                       @Field("carttotal") String total);

    @POST(CART_COUNT)
    Call<CartCountModel> getCartCount(@Header("LG-APP-KEY") String auth,
                                      @Header("Authorization") String token);

    @POST(POINTS)
    Call<PointsModel> getPoints(@Header("LG-APP-KEY") String auth,
                                @Header("Authorization") String token);

    @POST(HOME_OFFER_BANNER)
    Call<HomeOfferBannerModel> homeOfferBanner(@Header("LG-APP-KEY") String auth);


    @POST(DEALS_AND_OFFERS)
    Call<TimerOfferModel> dealsAndOffers(@Header("LG-APP-KEY") String auth,
                                         @Header("Authorization") String token);


    @POST(LIST_BUNDLE_PRODUCTS)
    Call<BundleProductModel> listBundleProducts(@Header("LG-APP-KEY") String auth,
                                                @Header("Authorization") String token);

    @FormUrlEncoded
    @POST(BUNDLE_DETAILS)
    Call<BundleDetailModel> bundleProductDetails(@Header("LG-APP-KEY") String auth,
                                                 @Header("Authorization") String token,
                                                 @Field("offers_id") String offerId);

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

    @FormUrlEncoded
    @POST(SUB_CAT_PRODUCT)
    Call<SubCategoryProducts> getSubcategoryProducts(@Header("LG-APP-KEY") String auth,
                                                     @Header("Authorization") String token,
                                                     @Field("subcategory_id") String categoryId);

    @POST(HOME_BEST_DEALS_CATEGORIES)
    Call<BestDealCategory> getDealsOffers(@Header("LG-APP-KEY") String auth,
                                          @Header("Authorization") String token);

    @POST(PERSONAL_INFO)
    Call<ProfileModel> getPersonalInfo(@Header("LG-APP-KEY") String auth,
                                       @Header("Authorization") String token);

    @POST(GOVERNARATE_LIST)
    Call<GovernarateModel> getGovernarateList(@Header("LG-APP-KEY") String auth);

    @POST(ALL_AREA_LIST)
    Call<AreaModel> getAllAreaList(@Header("LG-APP-KEY") String auth);

    @FormUrlEncoded
    @POST(AREA_LIST)
    Call<AreaModel> getAreaList(@Header("LG-APP-KEY") String auth,
                                @Field("governarate") String governarate);
    @FormUrlEncoded
    @POST(CITY_GOVERNARATE)
    Call<GovernarateModel> getCityGovernarate(@Header("LG-APP-KEY") String auth,
                                @Field("city_id") String cityId);

    @FormUrlEncoded
    @POST(DEL_CHARGE)
    Call<DeliveryChargeModel> getDeliveryCharge(@Header("LG-APP-KEY") String auth,
                                                @Header("Authorization") String token,
                                                @Field("area") String area,
                                                @Field("gtotal") String total,
                                                @Field("samedaystat") String isSameDay);

    @FormUrlEncoded
    @POST(DEL_CHECK)
    Call<DeliveryDateCheckModel> deliveryDateCheck(@Header("LG-APP-KEY") String auth,
                                                   @Header("Authorization") String token,
                                                   @Field("deliverytype") String delType,
                                                   @Field("delivery_date") String date,
                                                   @Field("sameday_available") String sameDay,
                                                   @Field("nextday_available") String nextDay);

    @FormUrlEncoded
    @POST(DEL_AVAILABILITY_CHECK)
    Call<DeliveryDateCheckModel> deliveryAvailabilityCheck(@Header("LG-APP-KEY") String auth,
                                                           @Header("Authorization") String token,
                                                           @Field("deliverytype") String delType);

    @POST(CHECKOUT_AVAILABILITY_CHECK)
    Call<CheckoutCheckModel> checkoutAvailabilityCheck(@Header("LG-APP-KEY") String auth,
                                                       @Header("Authorization") String token);

    @FormUrlEncoded
    @POST(DEL_DAYS_AVAILABILITY_CHECK)
    Call<DeliveryDaysCheckModel> deliveryAvailabilityDaysCheck(@Header("LG-APP-KEY") String auth,
                                                               @Header("Authorization") String token,
                                                               @Field("area") String area);

    @FormUrlEncoded
    @POST(CHECK_OUT)
    Call<PreCheckoutSuccessModel> preCheckout(@Header("LG-APP-KEY") String auth,
                                              @Header("Authorization") String token,
                                              @Field("adressid") String addressId,
                                              @Field("deliverytype") String delType,
                                              @Field("shipchrg") String shipCharge,
                                              @Field("coupon") String coupon,
                                              @Field("gtotalwithoutship") String total,
                                              @Field("samedaychrg") String sameDayCharge,
                                              @Field("walletapplied") String walletApplied,
                                              @Field("loyaltyapplied") String loyaltyApplied,
                                              @Field("order_note") String orderNote,
                                              @Field("dalivery_date") String delDate,
                                              @Field("delivery_time") String delTime,
                                              @Field("pickstore") String pickStore,
                                              @Field("delveryon") String delOn,
                                              @Field("platform") String platform);

    @POST(ORDER_HISTORY)
    Call<OrderListModel> getOrderList(@Header("LG-APP-KEY") String auth,
                                      @Header("Authorization") String token);

    @FormUrlEncoded
    @POST(ORDER_DETAILS)
    Call<OrderDetailsModel> getOrderDetails(@Header("LG-APP-KEY") String auth,
                                            @Header("Authorization") String token,
                                            @Field("orderid") String orderId);

    @POST(RETURN_REASONS)
    Call<OrderCancelReasons> getOrderReturnReasons(@Header("LG-APP-KEY") String auth,
                                                   @Header("Authorization") String token);

    @FormUrlEncoded
    @POST(RETURN_REQUEST)
    Call<CommonResponseModel> returnRequest(@Header("LG-APP-KEY") String auth,
                                            @Header("Authorization") String token,
                                            @Field("order_unique_id") String orderId,
                                            @Field("pid") String prodId,
                                            @Field("return_reason") String reasonId,
                                            @Field("return_reasontext") String message);

    @POST(NOTIFICATIONS)
    Call<NotificationModel> getNotificationList(@Header("LG-APP-KEY") String auth,
                                                @Header("Authorization") String token);

    @POST(WALLET)
    Call<WalletModel> getMyWallet(@Header("LG-APP-KEY") String auth,
                                  @Header("Authorization") String token);

    @POST(LOYALTY_POINTS)
    Call<LoyaltyModel> getMyLoyaltyPoints(@Header("LG-APP-KEY") String auth,
                                          @Header("Authorization") String token);

    @FormUrlEncoded
    @POST(WALLET_APPLIED)
    Call<CoinsAppliedModel> walletAppliedTotal(@Header("LG-APP-KEY") String auth,
                                               @Header("Authorization") String token,
                                               @Field("gtotalwithoutship") String total,
                                               @Field("walletapplied") String wallet);

    @FormUrlEncoded
    @POST(LOYALTY_APPLIED)
    Call<CoinsAppliedModel> loyaltyAppliedTotal(@Header("LG-APP-KEY") String auth,
                                                @Header("Authorization") String token,
                                                @Field("gtotalwithoutship") String total,
                                                @Field("loyaltyapplied") String loyalty);

    @POST(RETURN_POLICY)
    Call<ReturnPolicyModel> getReturnPolicy(@Header("LG-APP-KEY") String auth,
                                            @Header("Authorization") String token);

    @FormUrlEncoded
    @POST(RETURN_POLICY_MAIL)
    Call<CommonResponseModel> submitReturnPolicy(@Header("LG-APP-KEY") String auth,
                                                 @Header("Authorization") String token,
                                                 @Field("name") String name,
                                                 @Field("email") String email,
                                                 @Field("mobile") String mobile,
                                                 @Field("invoiceno") String invoiceno,
                                                 @Field("date") String date,
                                                 @Field("reason") String reason);

    @FormUrlEncoded
    @POST(CONTACT_MAIL_SEND)
    Call<CommonResponseModel> submitContactMail(@Header("LG-APP-KEY") String auth,
                                                @Header("Authorization") String token,
                                                @Field("name") String name,
                                                @Field("email") String email,
                                                @Field("mobile") String mobile,
                                                @Field("dept") String dept,
                                                @Field("address") String address);

    @POST(TERMS_CONDITIONS)
    Call<TermsContentModel> getTerms(@Header("LG-APP-KEY") String auth);

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

