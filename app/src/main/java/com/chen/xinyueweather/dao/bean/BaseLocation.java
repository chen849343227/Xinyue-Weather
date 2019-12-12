package com.chen.xinyueweather.dao.bean;

import java.util.List;

public class BaseLocation {


    /**
     * results : [{"address_components":[{"long_name":"Unnamed Road","short_name":"Unnamed Road","types":["route"]},{"long_name":"班玛县","short_name":"班玛县","types":["political","sublocality","sublocality_level_1"]},{"long_name":"果洛藏族自治州","short_name":"果洛藏族自治州","types":["locality","political"]},{"long_name":"青海省","short_name":"青海省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"Unnamed Road, 班玛县果洛藏族自治州青海省中国","geometry":{"bounds":{"northeast":{"lat":32.81684,"lng":100.4050076},"southwest":{"lat":32.7488854,"lng":100.281703}},"location":{"lat":32.7827814,"lng":100.3335585},"location_type":"GEOMETRIC_CENTER","viewport":{"northeast":{"lat":32.81684,"lng":100.4050076},"southwest":{"lat":32.7488854,"lng":100.281703}}},"place_id":"ChIJEUlEcNPzATcRui5inUFCRgo","types":["route"]},{"address_components":[{"long_name":"班玛县","short_name":"班玛县","types":["political","sublocality","sublocality_level_1"]},{"long_name":"果洛藏族自治州","short_name":"果洛藏族自治州","types":["locality","political"]},{"long_name":"青海省","short_name":"青海省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国青海省果洛藏族自治州班玛县","geometry":{"bounds":{"northeast":{"lat":33.3542668,"lng":101.2377647},"southwest":{"lat":32.5224198,"lng":99.76375089999999}},"location":{"lat":32.932723,"lng":100.737138},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":33.3542668,"lng":101.2377647},"southwest":{"lat":32.5224198,"lng":99.76375089999999}}},"place_id":"ChIJWWPy308f_jYRydvW32Uykmo","types":["political","sublocality","sublocality_level_1"]},{"address_components":[{"long_name":"果洛藏族自治州","short_name":"果洛藏族自治州","types":["locality","political"]},{"long_name":"青海省","short_name":"青海省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国青海省果洛藏族自治州","geometry":{"bounds":{"northeast":{"lat":35.6212793,"lng":101.7785482},"southwest":{"lat":32.5224198,"lng":96.9350597}},"location":{"lat":34.471431,"lng":100.244808},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":34.5924347,"lng":100.4260254},"southwest":{"lat":34.3512864,"lng":100.0717163}}},"place_id":"ChIJm4-BHLCoADcRfqtBls8G9JA","types":["locality","political"]},{"address_components":[{"long_name":"青海省","short_name":"青海省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国青海省","geometry":{"bounds":{"northeast":{"lat":39.2083441,"lng":103.0709706},"southwest":{"lat":31.5986622,"lng":89.40442449999999}},"location":{"lat":35.744798,"lng":96.4077358},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":39.2083441,"lng":103.0709706},"southwest":{"lat":31.5986622,"lng":89.40442449999999}}},"place_id":"ChIJ7Qtie5eHADcRv523fabL7Sg","types":["administrative_area_level_1","political"]},{"address_components":[{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国","geometry":{"bounds":{"northeast":{"lat":53.56097399999999,"lng":134.7728099},"southwest":{"lat":17.9996,"lng":73.4994136}},"location":{"lat":35.86166,"lng":104.195397},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":53.56097399999999,"lng":134.7728099},"southwest":{"lat":17.9996,"lng":73.4994136}}},"place_id":"ChIJwULG5WSOUDERbzafNHyqHZU","types":["country","political"]}]
     * status : OK
     */

    private String status;
    private List<AddressBean> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AddressBean> getResults() {
        return results;
    }

    public void setResults(List<AddressBean> results) {
        this.results = results;
    }

    public static class AddressBean {
        /**
         * address_components : [{"long_name":"Unnamed Road","short_name":"Unnamed Road","types":["route"]},{"long_name":"班玛县","short_name":"班玛县","types":["political","sublocality","sublocality_level_1"]},{"long_name":"果洛藏族自治州","short_name":"果洛藏族自治州","types":["locality","political"]},{"long_name":"青海省","short_name":"青海省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}]
         * formatted_address : Unnamed Road, 班玛县果洛藏族自治州青海省中国
         * geometry : {"bounds":{"northeast":{"lat":32.81684,"lng":100.4050076},"southwest":{"lat":32.7488854,"lng":100.281703}},"location":{"lat":32.7827814,"lng":100.3335585},"location_type":"GEOMETRIC_CENTER","viewport":{"northeast":{"lat":32.81684,"lng":100.4050076},"southwest":{"lat":32.7488854,"lng":100.281703}}}
         * place_id : ChIJEUlEcNPzATcRui5inUFCRgo
         * types : ["route"]
         */

        private String formatted_address;
        private GeometryBean geometry;
        private String place_id;
        private List<AddressComponentsBean> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<AddressComponentsBean> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponentsBean> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class GeometryBean {
            /**
             * bounds : {"northeast":{"lat":32.81684,"lng":100.4050076},"southwest":{"lat":32.7488854,"lng":100.281703}}
             * location : {"lat":32.7827814,"lng":100.3335585}
             * location_type : GEOMETRIC_CENTER
             * viewport : {"northeast":{"lat":32.81684,"lng":100.4050076},"southwest":{"lat":32.7488854,"lng":100.281703}}
             */

            private BoundsBean bounds;
            private LocationBean location;
            private String location_type;
            private ViewportBean viewport;

            public BoundsBean getBounds() {
                return bounds;
            }

            public void setBounds(BoundsBean bounds) {
                this.bounds = bounds;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public ViewportBean getViewport() {
                return viewport;
            }

            public void setViewport(ViewportBean viewport) {
                this.viewport = viewport;
            }

            public static class BoundsBean {
                /**
                 * northeast : {"lat":32.81684,"lng":100.4050076}
                 * southwest : {"lat":32.7488854,"lng":100.281703}
                 */

                private NortheastBean northeast;
                private SouthwestBean southwest;

                public NortheastBean getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBean northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBean getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBean southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBean {
                    /**
                     * lat : 32.81684
                     * lng : 100.4050076
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBean {
                    /**
                     * lat : 32.7488854
                     * lng : 100.281703
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class LocationBean {
                /**
                 * lat : 32.7827814
                 * lng : 100.3335585
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class ViewportBean {
                /**
                 * northeast : {"lat":32.81684,"lng":100.4050076}
                 * southwest : {"lat":32.7488854,"lng":100.281703}
                 */

                private NortheastBeanX northeast;
                private SouthwestBeanX southwest;

                public NortheastBeanX getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBeanX northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBeanX getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBeanX southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBeanX {
                    /**
                     * lat : 32.81684
                     * lng : 100.4050076
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBeanX {
                    /**
                     * lat : 32.7488854
                     * lng : 100.281703
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class AddressComponentsBean {
            /**
             * long_name : Unnamed Road
             * short_name : Unnamed Road
             * types : ["route"]
             */

            private String long_name;
            private String short_name;
            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }
    }
}
