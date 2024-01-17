package io.foodtechlab.replication;

/**
 * Репозиторий для реализации хранения репликации данных
 *
 * @param <DataModel>
 */
public interface ReplicaRepository<DataModel> {
    DataModel save(DataModel model);
    Boolean delete(String id);
}
