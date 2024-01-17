package io.foodtechlab.replication;

import lombok.RequiredArgsConstructor;

/**
 * Запускается при старте приложения для полной синхронизации данных
 * @param <T>
 */
@RequiredArgsConstructor
public class ReplicaSynchrinizator<T> {
    private final DataSourceGateway<T> dataSourceGateway;
    private final ReplicaRepository<T> replicaRepository;

    public void sync() {
       dataSourceGateway.findAll().forEach(replicaRepository::save);
    }
}
