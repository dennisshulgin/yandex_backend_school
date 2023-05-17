package ru.yandex.yandexlavka.jsons;

public class JsonStrings {
    public static String importCouriersReq = """
            {
              "couriers": [
                {
                  "courier_type": "FOOT",
                  "regions": [
                    1,2,3
                  ],
                  "working_hours": [
                    "11:00-12:00", "15:00-16:00"
                  ]
                },
                {
                  "courier_type": "BIKE",
                  "regions": [
                    1,2
                  ],
                  "working_hours": [
                    "15:00-17:00", "19:00-21:00"
                  ]
                },
                {
                  "courier_type": "AUTO",
                  "regions": [
                    4,5
                  ],
                  "working_hours": [
                    "10:00-12:00", "11:00-16:00"
                  ]
                }
              ]
            }""";

    public static String importCouriersRes = """
            {
              "couriers": [
                {
                  "regions": [
                    1,2,3
                  ],
                  "courier_id": 1,
                  "courier_type": "FOOT",
                  "working_hours": [
                    "11:00-12:00",
                    "15:00-16:00"
                  ]
                },
                {
                  "regions": [
                    1,2
                  ],
                  "courier_id": 2,
                  "courier_type": "BIKE",
                  "working_hours": [
                    "15:00-17:00",
                    "19:00-21:00"
                  ]
                },
                {
                  "regions": [
                    4,
                    5
                  ],
                  "courier_id": 3,
                  "courier_type": "AUTO",
                  "working_hours": [
                    "10:00-12:00",
                    "11:00-16:00"
                  ]
                }
              ]
            }
            """;
    public static String getCourierByIdRes = """
            {
                  "courier_id": 1,
                  "courier_type": "FOOT",
                  "working_hours": [
                    "11:00-12:00",
                    "15:00-16:00"
                  ],
                  "regions": [
                    1,2,3
                  ]
                }
            """;

    public static String getCouriersWithOffsetAndLimit = """
            {
              "couriers": [
                {
                  "regions": [
                    1,2
                  ],
                  "courier_id": 2,
                  "courier_type": "BIKE",
                  "working_hours": [
                    "15:00-17:00",
                    "19:00-21:00"
                  ]
                },
                {
                  "regions": [
                    4,
                    5
                  ],
                  "courier_id": 3,
                  "courier_type": "AUTO",
                  "working_hours": [
                    "10:00-12:00",
                    "11:00-16:00"
                  ]
                }
              ],
              "limit": 2,
              "offset": 1
            }
            """;
    public static String incorrectCourier = """
            {
              "couriers": [
                {
                  "courier_type": "LALALA",
                  "regions": [
                    1,2
                  ],
                  "working_hours": [
                    "11:45-15:07"
                  ]
                },
                {
                  "courier_type": "FOOT",
                  "regions": [
                    1,2
                  ],
                  "working_hours": [
                    "11:45-15:90"
                  ]
                }
              ]
            }
            
            """;

    public static String importOrdersReq = """
            {
              "orders": [
                {
                  "weight": 1.5,
                  "regions": 2,
                  "delivery_hours": [
                    "15:50-16:50", "19:50-20:50"
                  ],
                  "cost": 1500
                },
                {
                  "weight": 12.1,
                  "regions": 6,
                  "delivery_hours": [
                    "12:50-16:50"
                  ],
                  "cost": 5000
                },
                {
                  "weight": 111.5,
                  "regions": 4,
                  "delivery_hours": [
                    "15:50-16:50", "19:50-20:50", "12:00-12:30"
                  ],
                  "cost": 150
                }
              ]
            }
            """;
    public static String importOrdersRes = """
            [
                {
                    "order_id": 1,
                    "weight": 1.5,
                    "regions": 2,
                    "delivery_hours": [
                        "15:50-16:50",
                        "19:50-20:50"
                    ],
                    "cost": 1500,
                    "completed_time": null
                },
                {
                    "order_id": 2,
                    "weight": 12.1,
                    "regions": 6,
                    "delivery_hours": [
                        "12:50-16:50"
                    ],
                    "cost": 5000,
                    "completed_time": null
                },
                {
                    "order_id": 3,
                    "weight": 111.5,
                    "regions": 4,
                    "delivery_hours": [
                        "15:50-16:50",
                        "19:50-20:50",
                        "12:00-12:30"
                    ],
                    "cost": 150,
                    "completed_time": null
                }
            ]
            """;

    public static String getOrderByIdRes = """
            {
                    "order_id": 1,
                    "weight": 1.5,
                    "regions": 2,
                    "delivery_hours": [
                        "15:50-16:50",
                        "19:50-20:50"
                    ],
                    "cost": 1500,
                    "completed_time": null
            }
            """;

    public static String getOrdersWithOffsetAndLimit = """
            [
                {
                    "order_id": 2,
                    "weight": 12.1,
                    "regions": 6,
                    "delivery_hours": [
                        "12:50-16:50"
                    ],
                    "cost": 5000,
                    "completed_time": null
                },
                {
                    "order_id": 3,
                    "weight": 111.5,
                    "regions": 4,
                    "delivery_hours": [
                        "15:50-16:50",
                        "19:50-20:50",
                        "12:00-12:30"
                    ],
                    "cost": 150,
                    "completed_time": null
                }
            ]
            """;
    public static String completeOrderReq = """
            {
              "complete_info": [
                {
                  "courier_id": 1,
                  "order_id": 1,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                }
              ]
            }
            """;

    public static String completeOrderRes = """
            [
                {
                    "order_id": 1,
                    "weight": 1.5,
                    "regions": 2,
                    "delivery_hours": [
                        "15:50-16:50",
                        "19:50-20:50"
                    ],
                    "cost": 1500,
                    "completed_time": "2023-04-23T10:27:45.984"
                }
            ]
            """;

    public static String importAdditionalOrders = """
            {
              "orders": [
                {
                  "weight": 1.5,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:06"
                  ],
                  "cost": 150
                },
                {
                  "weight": 1.6,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:08"
                  ],
                  "cost": 160
                },
                {
                  "weight": 1.8,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:10"
                  ],
                  "cost": 117
                },
                {
                  "weight": 1.9,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:11"
                  ],
                  "cost": 156
                },
                {
                  "weight": 1.5,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:12"
                  ],
                  "cost": 150
                },
                {
                  "weight": 1.6,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:13"
                  ],
                  "cost": 160
                },
                {
                  "weight": 1.8,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:06"
                  ],
                  "cost": 117
                },
                {
                  "weight": 1.9,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:14"
                  ],
                  "cost": 156
                },
                {
                  "weight": 1.5,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:30"
                  ],
                  "cost": 150
                },
                {
                  "weight": 1.6,
                  "regions": 1,
                  "delivery_hours": [
                    "12:30-12:40"
                  ],
                  "cost": 160
                },
                {
                  "weight": 1.8,
                  "regions": 1,
                  "delivery_hours": [
                    "12:50-13:30"
                  ],
                  "cost": 117
                },
                {
                  "weight": 1.9,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:14"
                  ],
                  "cost": 156
                },
                {
                  "weight": 1.5,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:30"
                  ],
                  "cost": 150
                },
                {
                  "weight": 1.6,
                  "regions": 1,
                  "delivery_hours": [
                    "12:30-12:40"
                  ],
                  "cost": 160
                },
                {
                  "weight": 1.8,
                  "regions": 1,
                  "delivery_hours": [
                    "12:50-13:30"
                  ],
                  "cost": 117
                },
                {
                  "weight": 1.9,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:14"
                  ],
                  "cost": 156
                },
                {
                  "weight": 1.5,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:30"
                  ],
                  "cost": 150
                },
                {
                  "weight": 1.6,
                  "regions": 1,
                  "delivery_hours": [
                    "12:30-12:40"
                  ],
                  "cost": 160
                },
                {
                  "weight": 1.8,
                  "regions": 1,
                  "delivery_hours": [
                    "12:50-13:30"
                  ],
                  "cost": 117
                },
                {
                  "weight": 1.9,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:14"
                  ],
                  "cost": 156
                },
                {
                  "weight": 1.5,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:30"
                  ],
                  "cost": 150
                },
                {
                  "weight": 1.6,
                  "regions": 1,
                  "delivery_hours": [
                    "12:30-12:40"
                  ],
                  "cost": 160
                },
                {
                  "weight": 1.8,
                  "regions": 1,
                  "delivery_hours": [
                    "12:50-13:30"
                  ],
                  "cost": 117
                },
                {
                  "weight": 1.9,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:14"
                  ],
                  "cost": 156
                },
                {
                  "weight": 1.5,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:30"
                  ],
                  "cost": 150
                },
                {
                  "weight": 1.6,
                  "regions": 1,
                  "delivery_hours": [
                    "12:30-12:40"
                  ],
                  "cost": 160
                },
                {
                  "weight": 1.8,
                  "regions": 1,
                  "delivery_hours": [
                    "12:50-13:30"
                  ],
                  "cost": 117
                },
                {
                  "weight": 1.9,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:14"
                  ],
                  "cost": 156
                },
                {
                  "weight": 1.5,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:30"
                  ],
                  "cost": 150
                },
                {
                  "weight": 1.6,
                  "regions": 1,
                  "delivery_hours": [
                    "12:30-12:40"
                  ],
                  "cost": 160
                },
                {
                  "weight": 1.8,
                  "regions": 1,
                  "delivery_hours": [
                    "12:50-13:30"
                  ],
                  "cost": 117
                },
                {
                  "weight": 1.9,
                  "regions": 1,
                  "delivery_hours": [
                    "12:05-12:14"
                  ],
                  "cost": 156
                }
              ]
            }
            """;

    public static String completeAdditionalOrdersReq = """
            {
              "complete_info": [
                {
                  "courier_id": 1,
                  "order_id": 2,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 3,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 4,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 5,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 6,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 7,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 8,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 9,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 10,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 11,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 12,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 13,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 14,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 15,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 16,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 17,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 18,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 19,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 20,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 21,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 22,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 23,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 24,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 25,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 26,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 27,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 28,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 29,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 30,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 31,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 32,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 33,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 34,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                },
                {
                  "courier_id": 1,
                  "order_id": 35,
                  "complete_time": "2023-04-23T10:27:45.984Z"
                }
              ]
            }
            """;

    public static String ratingAndEarningRes = """
            {
                "regions":[1,2,3],
                "rating":3,
                "earning":22628,
                "courier_id":1,
                "courier_type":"FOOT",
                "working_hours":
                    [
                        "11:00-12:00",
                        "15:00-16:00"
                    ]
            }
            """;
}
