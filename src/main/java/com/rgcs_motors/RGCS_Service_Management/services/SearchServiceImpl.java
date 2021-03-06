package com.rgcs_motors.RGCS_Service_Management.services;

import com.rgcs_motors.RGCS_Service_Management.domain.Repair;
import com.rgcs_motors.RGCS_Service_Management.domain.User;
import com.rgcs_motors.RGCS_Service_Management.domain.Vehicle;
import com.rgcs_motors.RGCS_Service_Management.repositories.RepairRepository;
import com.rgcs_motors.RGCS_Service_Management.repositories.UserRepository;
import com.rgcs_motors.RGCS_Service_Management.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class SearchServiceImpl implements SearchService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private RepairRepository repairRepository;


    @Override
    public User searchUserByVatAndEmail(String vat, String email) throws Exception {
        User user;
        try {
            user= userRepository.findByEmailAndVat(email,vat);
            if(user == null)
            {
                throw new Exception("User not found!");
            }
        } catch (Exception e) {
            throw new Exception(e.getCause().toString());
        }
        return user;
    }

    @Override
    public User searchUserByVat(String vat) throws Exception {
        User user;
        try {
            user= userRepository.findByVat(vat);
            if(user == null)
            {
                throw new Exception("User not found!");
            }
        } catch (Exception e) {
            throw new Exception(e.getCause().toString());
        }
        return user;
    }

    @Override
    public User searchUserByEmail(String email) throws Exception {
        User user;
        try {
            user= userRepository.findByEmail(email);
            if(user == null)
            {
                throw new Exception("User not found!");
            }
        } catch (Exception e) {
            throw new Exception(e.getCause().toString());
        }
        return user;
    }

    @Override
    public List<Vehicle> searchVehicleByVatAndPlate(String uservatvat, String licenseplate) throws Exception {
        List<Vehicle> vehicles;
        try {
            vehicles = vehicleRepository.findByLicenseplateAndUservat(licenseplate,uservatvat);
            if(vehicles.isEmpty())
            {
                throw new Exception("Vehicle not found!");
            }
        } catch (Exception e) {
            throw new Exception(e.getCause().toString());
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> searchVehicleByVat(String uservat) throws Exception {
        List<Vehicle> vehicles;
        try {
            vehicles = vehicleRepository.findByUservat(uservat);
            if(vehicles.isEmpty())
            {
                throw new Exception("Vehicles not found!");
            }
        } catch (Exception e) {
            throw new Exception(e.getCause().toString());
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> searchVehicleByPlate(String licenseplate) throws Exception {
        List<Vehicle> vehicles;
        try {
            vehicles = vehicleRepository.findByLicenseplate(licenseplate);
            if(vehicles.isEmpty())
            {
                throw new Exception("Vehicle not found!");
            }
        } catch (Exception e) {
            throw new Exception(e.getCause().toString());
        }
        return vehicles;
    }

    @Override
    public List<Repair> searchRepairByPlate(String licenseplate) throws Exception {
        List<Repair> repairs;
        try {
            repairs = repairRepository.findByLicenseplate(licenseplate);
            if(repairs.isEmpty())
            {
                throw new Exception("Repair not found!");
            }
        } catch (Exception e) {
            throw new Exception(e.getCause().toString());
        }
        return repairs;
    }

    @Override
    public List<Repair> searchRepairByVat(String uservat) throws Exception {
        List<Repair> repairs = new ArrayList<>();
        List<Repair> tempRepairs;
        try {
            List<Vehicle> vehicles = vehicleRepository.findByUservat(uservat);
            for(Vehicle v:vehicles){
                tempRepairs = repairRepository.findByLicenseplate(v.getLicenseplate());
                for(Repair repair:tempRepairs)
                repairs.add(repair);
            }
            if(repairs.isEmpty())
            {
                throw new Exception("Repair not found!");
            }
        } catch (Exception e) {
            throw new Exception(e.getCause().toString());
        }
        System.out.println("repairs list size: " + repairs.size());
        return repairs;
    }

    @Override
    public List<Repair> searchRepairByDate(String date) throws Exception {
        List<Repair> repairs = null;
        try {
            repairs = repairRepository.findByRepairdateLessThanOrderByRepairdateAsc(date);
        if (repairs.isEmpty()){
            throw new Exception("Repair not found");
        }
        }catch (Exception e) {
            throw new Exception(e.getCause().toString());
        }
        return repairs;
    }

    @Override
    public List<Repair> searchRepairByDates(String date1, String date2) throws Exception {
        List<Repair> repairs = null;
        try {
            repairs = repairRepository.findByRepairdateBetweenOrderByRepairdateAsc(date1, date2);
            if (repairs.isEmpty()){
                throw new Exception("Repair not found");
            }
        }catch (Exception e) {
            throw new Exception(e.getCause().toString());
        }
        return repairs;
    }
}
