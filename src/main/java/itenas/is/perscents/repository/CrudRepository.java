/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itenas.is.perscents.repository;

import java.util.List;

/**
 *
 * @author ASUS
 */
public interface CrudRepository<T> {
    List<T> findAll();
}
