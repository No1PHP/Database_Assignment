package service;

import dao.DAOInterfaces.MaterialOrderRepository;
import dao.DAOInterfaces.MaterialRepository;
import dao.DAOInterfaces.OperationRecordRepository;
import dao.DAO_Type;
import dao.tables.Material;
import dao.tables.MaterialOrder;

import java.sql.Date;
import java.util.List;

public class Service {
    public float getMaterialUsageDuring(String materialName, Date from, Date until) {
        MaterialRepository materialRepository = (MaterialRepository) DAO_Type.MATERIAL.getTableRepository();
        Material material = materialRepository.findByName(materialName);

        OperationRecordRepository operationRecordRepository = (OperationRecordRepository) DAO_Type.OPERATION_RECORD.getTableRepository();
    }
}
