package com.weshop.deliveryboy.api;

import com.weshop.deliveryboy.models.DriverDetails;
import com.weshop.deliveryboy.models.MenufactureDetails;
import com.weshop.deliveryboy.models.OrdersResponse;
import com.weshop.deliveryboy.models.SettingsData;

import java.util.List;

public class ServerData {

    public static SettingsData settingsData;

    public static DriverDetails currentDriver;
    public static OrdersResponse ordersResponse;

    public static List<MenufactureDetails> dashboardObjectsList;
    public static List<MenufactureDetails> historyObjectsList;

    public static String MAPTYPE = "internal";      // external / internal
}
