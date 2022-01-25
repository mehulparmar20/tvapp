package com.yahumott.tvapp.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        //rewrite the request to add bearer token
        Request newRequest=chain.request().newBuilder()
                .header("Authorization","Bearer "+ "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiZWU0ODQxN2Y1OWJjMDg1ZTA3NTExMjI5NGQzZWUxMTYyMTI4ODFlZjc0ZTJhOTczNzczMjk0NTQxMzM3YmRiMjA0NzdlMTI1MTM1NzhjODAiLCJpYXQiOjE2Mzc5MDE0MzYuMTE4OTc0OTI0MDg3NTI0NDE0MDYyNSwibmJmIjoxNjM3OTAxNDM2LjExODk4MDg4NDU1MjAwMTk1MzEyNSwiZXhwIjoxNjY5NDM3NDM2LjEwNDkxNzA0OTQwNzk1ODk4NDM3NSwic3ViIjoiMSIsInNjb3BlcyI6W119.zsl-ibcDblLwinYNvpf1xCPjKiZg1GgVpZim-sTDBYrxAaS82dci4szD_5HLahK9v0gN7qjhDhuEb6j554EMjywv3MGfRBBDzfiWua4gw9Bi03GF_l17kLLEfphDsJYPcDkkhzzk8vBWinTlPStbOb9PSrbxf1TPx1-GrPfKfD5C9Pu1SKQT_8Em6V_QFPykrvJloaRvgr_BX9-P5F1LL_1lAPEZttDGHpIRZWroBanbePteRjOPwdlDliao48HOQkOL19ga-l-vFDjNvGfsxK6DuXNdQlF0l6u3oG6CnfmDVCO4bU_98I4lWvwDlLA0Fr4lhWJs1C9GueLIKZStBWZ4hXVeFX_3kw_gFdGyucxvEvJ-3jb1boQcIauQyK17DXkfEoYyiHpmybnJI7Y10VPuPNhgo3tMIRtfS2fuvQYzDf0B_YztgNX6nrYIO6fUS6YApueLW2Ywz8jBpBe6JmRXl7McJnOSexp1bRrVgttYFBSwH_DqLfZRAqc4em29tN1WnD0XeUyuRu-6WanAaskwTrG-p0OAmQiZqdH3e0e4Ym-xbHuS_OOutQTbodhswHmi1wlLu1HnvC4gbZKE_fRbFoBGQSyfdaV37Y0dsM7pkh084N_GauKGdO2KPeU8LA_JrRFgtVuFdD-x8MDE7nQ5mtwuPn6FX5JEiIORg3I")
                .build();

        return chain.proceed(newRequest);
    }
}
