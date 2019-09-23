/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.portal.models;

import com.microsoft.azure.storage.table.TableServiceEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LogsStorage extends TableServiceEntity {

    String application;
    String logs;
    String type;

    public LogsStorage(String application, String item) {
        this.partitionKey = application;
        this.rowKey = item;
    }

}
