package com.waracle.cakemgr.service;

import com.waracle.cakemgr.Cake;

import java.io.IOException;
import java.util.List;


public interface CakeService {

   List<Cake> parseCake(StringBuffer stringBuffer) throws IOException;
}
