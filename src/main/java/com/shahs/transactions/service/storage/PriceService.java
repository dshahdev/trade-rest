package com.shahs.transactions.service.storage;

import com.shahs.transactions.model.*;
import com.shahs.transactions.repository.*;
import com.shahs.transactions.util.MiscUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PriceService {

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    ParameterRepository parameterRepository;

    @Autowired
    AllocRepository allocRepository;


}
