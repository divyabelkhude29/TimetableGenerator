package com.timetable.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.HolidayDAO;
import com.timetable.dto.holiday.HolidayRequest;
import com.timetable.dto.holiday.HolidayResponse;
import com.timetable.entity.Holiday;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.HolidayMapper;
import com.timetable.service.HolidayService;

@Service
@Transactional
public class HolidayServiceImpl implements HolidayService {

    private final HolidayDAO holidayDAO;

    public HolidayServiceImpl(HolidayDAO holidayDAO) {
        this.holidayDAO = holidayDAO;
    }

    /**
     * Save Holiday
     */
    @Override
    public HolidayResponse saveHoliday(HolidayRequest request) {

        if (holidayDAO.existsByHolidayDate(request.getHolidayDate())) {

            throw new IllegalArgumentException(
                    "Holiday already exists for date : "
                            + request.getHolidayDate());
        }

        Holiday holiday = HolidayMapper.toEntity(request);

        holiday = holidayDAO.save(holiday);

        return HolidayMapper.toResponse(holiday);
    }

    /**
     * Update Holiday
     */
    @Override
    public HolidayResponse updateHoliday(
            Long holidayId,
            HolidayRequest request) {

        Holiday holiday = holidayDAO.findById(holidayId);

        if (holiday == null) {

            throw new ResourceNotFoundException(
                    "Holiday not found with ID : "
                            + holidayId);
        }

        if (holidayDAO.existsByHolidayDateAndNotHolidayId(
                request.getHolidayDate(),
                holidayId)) {

            throw new IllegalArgumentException(
                    "Another holiday already exists for date : "
                            + request.getHolidayDate());
        }

        HolidayMapper.updateEntity(
                holiday,
                request);

        holiday = holidayDAO.update(holiday);

        return HolidayMapper.toResponse(holiday);
    }

    /**
     * Delete Holiday
     */
    @Override
    public void deleteHoliday(Long holidayId) {

        Holiday holiday = holidayDAO.findById(holidayId);

        if (holiday == null) {

            throw new ResourceNotFoundException(
                    "Holiday not found with ID : "
                            + holidayId);
        }

        holidayDAO.delete(holidayId);
    }

    /**
     * Get Holiday By ID
     */
    @Override
    @Transactional(readOnly = true)
    public HolidayResponse getHolidayById(Long holidayId) {

        Holiday holiday = holidayDAO.findById(holidayId);

        if (holiday == null) {

            throw new ResourceNotFoundException(
                    "Holiday not found with ID : "
                            + holidayId);
        }

        return HolidayMapper.toResponse(holiday);
    }

    /**
     * Get All Holidays
     */
    @Override
    @Transactional(readOnly = true)
    public List<HolidayResponse> getAllHolidays() {

        return holidayDAO.findAll()
                .stream()
                .map(HolidayMapper::toResponse)
                .collect(Collectors.toList());
    }
    /**
     * Get Holiday By Date
     */
    @Override
    @Transactional(readOnly = true)
    public HolidayResponse getHolidayByDate(
            LocalDate holidayDate) {

        Holiday holiday = holidayDAO.findByHolidayDate(holidayDate);

        if (holiday == null) {

            throw new ResourceNotFoundException(
                    "Holiday not found for date : "
                            + holidayDate);
        }

        return HolidayMapper.toResponse(holiday);
    }

    /**
     * Check Whether Date Is Holiday
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isHoliday(LocalDate holidayDate) {

        return holidayDAO.isHoliday(holidayDate);
    }
}