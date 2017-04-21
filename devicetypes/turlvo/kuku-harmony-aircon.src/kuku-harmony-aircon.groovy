/**
 *  KuKu Harmony - Virtual Switch for Logitech Harmony
 *
 *  Copyright 2017 KuKu <turlvo@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

metadata {
	definition (name: "KuKu Harmony_Aircon", namespace: "turlvo", author: "KuKu") {
        capability "Actuator"
		capability "Switch"
		capability "Refresh"
		capability "Sensor"
        capability "Configuration"
        capability "Health Check"
        
        command "power"
        command "tempup"
        command "mode"
        command "jetcool"
        command "tempdown"
        command "speed"
        command "setRangedLevel", ["number"]
	}

	tiles (scale: 2){      
		multiAttributeTile(name:"switch", type: "generic", width: 6, height: 4, canChangeIcon: true){
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "off", label:'${name}', action:"switch.on", backgroundColor:"#ffffff", icon: "st.switches.switch.off", nextState:"turningOn"
				attributeState "on", label:'${name}', action:"switch.off", backgroundColor:"#79b821", icon: "st.switches.switch.on", nextState:"turningOff"				
				attributeState "turningOn", label:'${name}', action:"switch.off", backgroundColor:"#79b821", icon: "st.switches.switch.off", nextState:"turningOff"
				attributeState "turningOff", label:'${name}', action:"switch.on", backgroundColor:"#ffffff", icon: "st.switches.switch.on", nextState:"turningOn"
			}
        }

        valueTile("power", "device.power", width: 2, height: 2, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "yes", label: "POWER", action: "power"
            state "no", label: "unavail", action: ""
        }
        valueTile("tempup", "device.tempup", width: 2, height: 2, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "yes", label: "∧\nTEMP", action: "tempup"
            state "no", label: "unavail", action: ""
        }
        valueTile("mode", "device.mode", width: 2, height: 2, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "yes", label: "MODE", action: "mode"
            state "no", label: "unavail", action: ""
        }
        
        valueTile("jetcool", "device.jetcool", width: 2, height: 2, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "yes", label: "JET\nMODE", action: "jetcool"
            state "no", label: "unavail", action: ""
        }
        valueTile("tempdown", "device.tempdown", width: 2, height: 2, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "yes", label: "TEMP\n∨", action: "tempdown"
            state "no", label: "unavail", action: ""
        }
        valueTile("speed", "device.speed", width: 2, height: 2, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "yes", label: "FAN SPEED", action: "speed"
            state "no", label: "unavail", action: ""
        }
        controlTile("tempSliderControl", "device.level", "slider", range:"(18..30)", height: 2, width: 4) {
            state "level", action:"setRangedLevel"
        }
        valueTile("tempSliderControlValue", "device.level", height: 2, width: 2) {
			state "range", label:'Temperature\n${currentValue}°C', defaultState: true
		}

    }

	main(["switch"])
	details(["power", "tempup", "mode",
            "jetcool", "tempdown", "speed", 
            "tempSliderControl", "tempSliderControlValue"])
}

def installed() {
	log.debug "installed()"
	//configure()
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

def power() {
    log.debug "child power()"
    log.debug "power>> ${device.currentState("switch")?.value}"
    def currentState = device.currentState("switch")?.value

    if (currentState == "on") {
		off()
    } else {
        on()
    }
}

def tempup() {
    log.debug "child tempup()"
    parent.command(this, "tempup")
}

def mode() {
    log.debug "child mode()"
    parent.command(this, "mode")
}

def jetcool() {
    log.debug "child jetcool()"
    parent.command(this, "jetcool")
}

def tempdown() {
    log.debug "child tempdown()"
    parent.command(this, "tempdown")
}

def speed() {
    log.debug "child speed()"
    parent.command(this, "speed")
    
}

def setRangedLevel(value) {
	log.debug "setting ranged level to $value"
	parent.commandValue(this, value)
    sendEvent(name:"level", value:value)
}

def on() {
	log.debug "child on()"
	parent.command(this, "power-on")
    sendEvent(name: "switch", value: "on")
}

def off() {
	log.debug "child off"
	parent.command(this, "power-off")
    sendEvent(name: "switch", value: "off")
}

def poll() {
	log.debug "poll()"
}

def parseEventData(Map results) {
    results.each { name, value ->
        //Parse events and optionally create SmartThings events
    }
}