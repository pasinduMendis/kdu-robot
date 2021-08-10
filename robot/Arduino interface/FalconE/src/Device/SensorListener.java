/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Device;

import java.util.EventListener;

/**
 *
 * @author #ravindu_n
 */
public interface SensorListener extends EventListener {

    public void sensorReceived(String f);

}
