package com.itheima.stock.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 金宗文
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock4DescriptionDomain implements Serializable {
    private String code;
    private String name;
    private String trade;
    private String business;
}
