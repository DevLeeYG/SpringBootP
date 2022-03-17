package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor//새로붙임
@RequiredArgsConstructor
class DemoModel {

    private String id;

}
