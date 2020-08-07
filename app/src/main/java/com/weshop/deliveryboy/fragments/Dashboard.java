package com.weshop.deliveryboy.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.weshop.deliveryboy.Listeners.DashboardRVListener;
import com.weshop.deliveryboy.MainActivity;
import com.weshop.deliveryboy.R;
import com.weshop.deliveryboy.adapters.DashboardRVAdapter;
import com.weshop.deliveryboy.api.RetrofitClient;
import com.weshop.deliveryboy.api.ServerData;
import com.weshop.deliveryboy.models.OrdersResponse;
import com.weshop.deliveryboy.utils.OrderStatuses;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends Fragment {

    public static RecyclerView recyclerView;

    public static DashboardRVAdapter adapter;
    private Context context;
    private List<String> items;

    public static ProgressBar progressBar;
    LinearLayout emptyLayout;


    public Dashboard() {

        context = getActivity();
        items = new ArrayList<>();
        items.add("Pending");
        items.add("Confirm");
        items.add("Out For Delivery");
        items.add("Delivered");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        MainActivity.refreshbutton.setVisibility(View.VISIBLE);
        MainActivity.statusLayout.setVisibility(View.GONE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        context = view.getContext();

        emptyLayout = view.findViewById(R.id.emptyLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);

        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);


        Call<OrdersResponse> call = RetrofitClient.getInstance().gatDeliveryOrders("1", ServerData.currentDriver.getData().get(0).getPassword() + "");
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equals("1")) {

                        ServerData.ordersResponse = response.body();
                        ServerData.dashboardObjectsList = new ArrayList<>();
                        ServerData.historyObjectsList = new ArrayList<>();


                        for (int i = 0; i < ServerData.ordersResponse.getData().size(); i++) {
                            if (ServerData.ordersResponse.getData().get(i).getOrdersStatusId() == OrderStatuses.COMPLETED
                                    || ServerData.ordersResponse.getData().get(i).getOrdersStatusId() == OrderStatuses.CANCEL) {
                                ServerData.historyObjectsList.add(ServerData.ordersResponse.getData().get(i));
                            } else {
                                ServerData.dashboardObjectsList.add(ServerData.ordersResponse.getData().get(i));
                            }
                        }


                        //Adapter
                        adapter = new DashboardRVAdapter(getActivity(), items, new DashboardRVListener() {
                            @Override
                            public Void onItemClick(View v, int position) {
                                return null;
                            }
                        });

                        if (ServerData.dashboardObjectsList.size() > 0)
                            emptyLayout.setVisibility(View.GONE);
                        else
                            emptyLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        //recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                    } else {
                        emptyLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    emptyLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                emptyLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
