package com.companyz.ems.repository;

import java.util.List;

import com.companyz.ems.exception.DatabaseException;

/**
 * Generic repository interface defining CRUD operations.
 * Provides a contract for data access operations on entities.
 * 
 * @param <T> the type of entity this repository manages
 * @author Company Z Development Team
 * @version 1.0
 */
public interface Repository<T> {

    /**
     * Finds an entity by its ID.
     * 
     * @param id the entity ID
     * @return the entity, or null if not found
     * @throws DatabaseException if a database error occurs
     */
    T findById(int id) throws DatabaseException;

    /**
     * Retrieves all entities.
     * 
     * @return a list of all entities
     * @throws DatabaseException if a database error occurs
     */
    List<T> findAll() throws DatabaseException;

    /**
     * Saves a new entity to the database.
     * 
     * @param entity the entity to save
     * @throws DatabaseException if a database error occurs
     */
    void save(T entity) throws DatabaseException;

    /**
     * Updates an existing entity in the database.
     * 
     * @param entity the entity to update
     * @throws DatabaseException if a database error occurs
     */
    void update(T entity) throws DatabaseException;

    /**
     * Deletes an entity by its ID.
     * 
     * @param id the entity ID to delete
     * @throws DatabaseException if a database error occurs
     */
    void delete(int id) throws DatabaseException;
}
