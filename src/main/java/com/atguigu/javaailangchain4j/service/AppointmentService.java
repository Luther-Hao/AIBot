package com.atguigu.javaailangchain4j.service;

import com.atguigu.javaailangchain4j.entity.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AppointmentService extends IService<Appointment> {
    Appointment getOne(Appointment appointment);
}
