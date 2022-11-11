package com.sszkoluda.interview.service.data;

import com.sszkoluda.interview.domain.HistoricRoute;

import java.io.InputStream;
import java.util.List;

public interface DataImporter {

    List<HistoricRoute> importData(InputStream inputFile);
}
