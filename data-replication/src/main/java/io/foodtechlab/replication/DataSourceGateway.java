package io.foodtechlab.replication;

import java.util.List;

/**
 * Взаимодействие с источником данных
 *
 * @param <DataModel>
 */
public interface DataSourceGateway<DataModel> {
    List<DataModel> findAll();
}
