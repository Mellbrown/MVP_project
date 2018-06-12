package com.techwork.kjc.mvp_project.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.bean.MeasureItemBean;

import java.util.ArrayList;


/*
 * 자 여러분, 이제 액티비티의 intent의 복잡성을 갖다버리고 Fragment로 사용합시다.
 * 우리가 사용하게 뭐든 이전 것보다 적합합니다. 짱짱! 액티비티와 다르게 매니페스트에 이것을 등록할 필요 없습니다.
 * 그리고 Activity 기능 거의 대부분을 이용할 수 있지만 자제 해주세요. Context나 PackageName정도만 해줬으면...
 * Fragment로 View만들기는 그냥 빈 클래스를 만드는 것 부터 시작합니다. 이제 다음 순서를 따라 작업하면 되요.
 *
 * 1. 빈 클래스를 만든다.
 * 2. Fragment를 상속 받는다. (주의!, android.app.Fragment 아니고 android.support.v4.app.Fragment 로 해주세요. 호환성 문제)
 */
public class FRG0_Example extends Fragment {

    // 이 프래그먼트의 전개된 레이아웃 담는 변수
    private View viewLayout;

    // 예시 UI
    private Button expUI1;
    private ImageView expUI2;

    // 12. 하지만 역시 Requester객체를 외부에서 구현해서 넘겨줘야
    //     11.번 코드를 작성이 가능한거죠. 외부에서 Reqeust객체를 알아서 구현해서 넣을 수 있게
    //     public으로 변수 만들어 주시면 외부에서 알아서 구현해서 넣어 줄것이애오
    public Requester requester;

    //3. onCreateView를 오버라이딩 해주세요. (필수, 일단 Activity의 onCreate의 내용 작성하는 것처럼 쓰면 됨
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // 4. 이 내용을 지워 주시고
        //return super.onCreateView(inflater, container, savedInstanceState);

        // 5. 이 코드를 써줍니다. 이 프래그먼트에 사용할 레이아웃을 전개해서 뷰를 반환해줍니다. 근데 이게 끝이 아니고
        viewLayout = inflater.inflate(R.layout.act1_splash, container,false);

        // 7. 그냥 findViewById가 안먹힙니다. 전개된 레이아웃에서 findViewById를 사용합니다.
        expUI1 = viewLayout.findViewById(R.id.btnReload); //암거나 불러왔어요. 예시닌까
        expUI2 = viewLayout.findViewById(R.id.act2_image);

        expUI1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 11. 그럼 이렇게 데이터나 다른 서비스가 필요할때 오면 그때 Requester interface 객체의 메소드를 호출하는 것이져
                requester.requestMeasureItemBeans();
            }
        });

        // 8. 이후 이벤트 연결이나 딴 작업은 알아서 해주시고
        //    Intent통해서 아무것도 받아오는 작업은 없어요.

        // 6. 이렇게 반환을 해줘야 레이아웃을 적용하는 것이 끝납니다.
        return viewLayout;
    }

    // 9. 그래도 비동적인 데이터 불러오기와 복잡한 로직은 그냥 저번 처럼 interface를 요청하세요
    public interface Requester{
        void requestMeasureItemBeans(); //그냥 큰데이터를 달라고 이렇게 얘기를 합니다.
    }

    // 10. 그럼 이렇게 public response 메소드를 만들어둬서 응답을 여기서 할수 있게 해주세요 (인자로 필요한 데이터 기술)
    public void responseMeasureItemBeans(ArrayList<MeasureItemBean> measureItemBeans){
        // 여기서 일련의 아이템 리스트를 받아오게 된겁니다.
        // 준비 다됬으면 외부에서 이것을 호출해서 넘겨주는 거란 말이져
        // 아래코드는 받아온 데이터로 Reqcyler뷰에 적용하는 코드였습니다.(FRG5 내용꺼임)

        // recAdapter.dataList = measureItemBeans;
        // recAdapter.notifyDataSetChanged();
    }
}
