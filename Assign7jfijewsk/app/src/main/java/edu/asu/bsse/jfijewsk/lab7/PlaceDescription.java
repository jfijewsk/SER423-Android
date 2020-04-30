package edu.asu.bsse.jfijewsk.lab7;

/*
 PlaceDescription.java
 Assign7jfijewsk
 Created by James on 3/18/20.
 Copyright © 2020 James. All rights reserved.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 @author   James Fijewski   mailto:jfijewsk@asu.edu.
 @version March 30, 2020
 */

public class PlaceDescription {

    private String name;
    private String description;
    private String category;
    private String address_title;
    private String address;
    private Double elevation;
    private Double latitude;
    private Double longitude;

    // Constructors
    public PlaceDescription(String name, String description, String category, String address_title, String address, Double elevation, Double latitude, Double longitude){
        this.name = name;
        this.description = description;
        this.category = category;
        this.address_title = address_title;
        this.address = address;
        this.elevation = elevation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getAddress_title() {
        return address_title;
    }

    public String getAddress() {
        return address;
    }

    public Double getElevation() {
        return elevation;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
