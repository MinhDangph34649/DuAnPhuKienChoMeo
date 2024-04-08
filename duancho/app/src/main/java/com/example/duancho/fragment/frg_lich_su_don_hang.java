package com.example.duancho.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.duancho.Dao.DonHangDao;
import com.example.duancho.Interface.OnItemClick;
import com.example.duancho.Model.DonHang;
import com.example.duancho.R;
import com.example.duancho.adapter.Adapter_lich_su_don_hang;
import com.example.duancho.databinding.FragmentFrgLichSuDonHangBinding;

import java.util.ArrayList;


public class frg_lich_su_don_hang extends Fragment {
    private String www;



    public frg_lich_su_don_hang() {
        // Required empty public constructor
    }
    FragmentFrgLichSuDonHangBinding binding;
    private ArrayList<DonHang> list = new ArrayList<>();
    private DonHangDao dao;
    private Adapter_lich_su_don_hang adapterDonHang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgLichSuDonHangBinding.inflate(inflater,container,false);
        SharedPreferences preferences = getActivity().getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
        int mand = preferences.getInt("mataikhoan", 0);

        dao = new DonHangDao(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rcvLichSuDonHang.setLayoutManager(layoutManager);
        list = dao.getDonHangByMaTaiKhoan(mand);
        adapterDonHang = new Adapter_lich_su_don_hang(list,getActivity());
        binding.rcvLichSuDonHang.setAdapter(adapterDonHang);

        adapterDonHang.setOnItemClick(new OnItemClick() {
            @Override
            public void onItemClick(int position) {
                DonHang donHang = list.get(position);
                int maDonHang = donHang.getMaDonHang();

                Bundle bundle = new Bundle();
                bundle.putInt("maDonHang", maDonHang);
                frg_ls_don_hang_chi_tiet frgLsDonHangChiTiet = new frg_ls_don_hang_chi_tiet();
                frgLsDonHangChiTiet.setArguments(bundle);
                FragmentManager fragmentManager = frg_lich_su_don_hang.this.getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                fragmentTransaction.replace(R.id.frameLayoutMain, frgLsDonHangChiTiet);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return binding.getRoot();
    }
}