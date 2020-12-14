package com.waracle.cakemgr.service;

import com.waracle.cakemgr.entity.Cake;

import java.io.IOException;
import java.util.Set;


public interface CakeService {

   Set<Cake> parseCake(StringBuffer stringBuffer) throws IOException;
}
