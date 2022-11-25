#############
Documentation for the Arsinoe Data Management Service
#############

.. |catalogueBaseURL| replace:: https://arsinoe-dev.madgik.di.uoa.gr
.. |serviceBaseURL| replace:: https://arsinoe-dev.madgik.di.uoa.gr/data-management-service


The Arsinoe Data Management Service provides endpoints to simplify the machine-to-machine interaction with the Arsinoe Data Catalogue.

*************
Data Management Service Endpoints
*************
The endpoints listed below may also receive an API token in the Authorization header. An API token can be issued by navigating to the profile page and clicking on the API Tokens tab. Alternatively, the auto-generated API Key, found at the bottom-left corner of the screen of the profile can be used. Including a token in the requests identifies the user and yields results according to the user's rights on the platform. Otherwise, only public elements of the platform will be returned. 
The base URL of the Data Catalogue is |catalogueBaseURL| and the base URL for the data management service is |serviceBaseURL|

===========
List Case Studies
===========

.. http:get:: /list-case-studies
   
   Retrieves a list of the case studies in the Data Catalogue.
   
   :requestheader Authorization: optional `API token`

**Example Request**

.. sourcecode:: bash
  
    curl --request GET ' {baseURL}/list-case-studies' --header 'Authorization: token'

**Example Response**

.. sourcecode:: json

   [
      {
         "id": "408463d1-b4a2-40a6-bca6-5b69ee3b26a2",
         "title": "Case Study 1",
         "name": "case-study-1",
         "created": "2022-10-20T12:05:38.619616",
         "description": "A description for case study 1",
         "image_url": "https://arsinoe-project.eu/securstorage/2022/02/cs1.jpg"
      },
      {
         "id": "5fc66e62-9566-4ac7-8060-956dcf776bdc",
         "title": "Case Study 2",
         "name": "case-study-2",
         "created": "2022-10-20T13:05:38.619616",
         "description": "A description for case study 2",
         "image_url": ""
      },
   ]


===========
List Datasets
===========

.. http:get:: /list-datasets

   Retrieves a list of the datasets in the Data Catalogue.

   :query string:  case_study_id (*optional*) -- The id of the case study. Specifies the case study to which the datasets belong. If not provided, then all datasets in the catalogue are returned.

   :requestheader Authorization: optional `API token`

   :statuscode 404: The provided `case_study_id` does not match a case study in the Data Catalogue.

**Example Request**

.. sourcecode:: bash
  
   curl --request GET '{baseURL}/list-datasets' --header 'Authorization: token'

**Example Response**

.. sourcecode:: json

   [
      {
         "id": "d551d2ff-0902-4576-a610-942e230faaa4",
         "title": "Natura 2000",
         "name": "natura-2000",
         "description": "Natura 2000 datasets",
         "author": "Eurostat",
         "maintainer": "Eurostat",
         "doi": "https://doi.org/10.1007/s10531-021-02125-7",
         "origin": "primary",
         "resources": [
            {
               "id": "dea64b6b-5bf7-4698-bb88-d095be9c4ccb",
               "created": "2022-10-25T12:50:48.367494",
               "description": "natura 2000 greece in shapefile format",
               "format": "SHP",
               "name": "natura-2000-greece",
               "size": 5478508,
               "url": "{catalogueBaseURL}/dataset/d551d2ff-0902-4576-a610-942e230faaa4/resource/dea64b6b-5bf7-4698-bb88-d095be9c4ccb/download/262a95fb-2d88-4df8-980f-5ed4de44245b.zip",
               "last_modified": "2022-10-25T12:50:48.330817",
               "resource_type": null
            }
         ],
         "license_id": "other-at",
         "license_title": "Other (Attribution)",
         "license_url": null,
         "private": true,
         "case_study_id": "408463d1-b4a2-40a6-bca6-5b69ee3b26a2",
         "number_of_resources": 1,
         "publication_date": "2021-09-17",
         "authorEmail": "",
         "maintainer_email": "",
         "resource_type": null,
         "dataset_type": "geospatial"
      },
   ]

===========
Retrieve Dataset Info
===========

.. http:get:: /dataset-info

   Retrieves the information of the specified dataset.

   :query string:  dataset_id (*required*) -- The id of the dataset.

   :requestheader Authorization: optional `API token`

   :statuscode 404: No dataset was found with the provided id.

**Example Request**

.. sourcecode:: bash
  
   curl --request GET '{baseURL}/dataset-info?dataset_id=d551d2ff-0902-4576-a610-942e230faaa4' --header 'Authorization: token'

**Example Response**

Same as in `List Datasets`_ but instead of a list, returns a single object.

===========
Retrieve Resource Info
===========

.. http:get:: /resource-info

   Retrieves the information of the specified resource.

   :query string:  resource_id (*required*) -- The id of the resource.

   :requestheader Authorization: optional `API token`

   :statuscode 404: No resource was found with the provided id.

**Example Request**

.. sourcecode:: bash
  
   curl --request GET '{baseURL}/resource-info?resource_id=dea64b6b-5bf7-4698-bb88-d095be9c4ccb' --header 'Authorization: token'

**Example Response**

.. sourcecode:: json

   {
      "id": "dea64b6b-5bf7-4698-bb88-d095be9c4ccb",
      "created": "2022-10-25T12:50:48.367494",
      "description": "natura 2000 greece in shapefile format",
      "format": "SHP",
      "name": "natura-2000-greece",
      "size": 5478508,
      "url": "{catalogueBaseURL}/dataset/d551d2ff-0902-4576-a610-942e230faaa4/resource/dea64b6b-5bf7-4698-bb88-d095be9c4ccb/download/262a95fb-2d88-4df8-980f-5ed4de44245b.zip",
      "last_modified": "2022-10-25T12:50:48.330817",
      "resource_type": null
   }

===========
Download Resource
===========

.. http:get:: /download-resource

   Redirects to the download url of the resource in the Data Catalogue.

   :query string:  resource_id (*required*) -- The id of the resource.

   :requestheader Authorization: optional `API token`

   :statuscode 301: The resource download url was retrieved successfully.

   :statuscode 404: No resource was found with the provided id.

**Example Request**

.. sourcecode:: bash
  
   curl --location --request GET '{baseURL}/resource-info?resource_id=dea64b6b-5bf7-4698-bb88-d095be9c4ccb' --header 'Authorization: token'

.. note::

   The `--location` option instructs curl to follow redirects.

===========
List Groups
===========

.. http:get:: /list-groups

   Retrieves a list of the groups in the Data Catalogue.

   :requestheader Authorization: optional `API token`

**Example Request**

.. sourcecode:: bash

   curl --request GET '{serviceBaseURL}/list-groups' --header 'Authorization: token'

**Example Response**

.. sourcecode:: json

   [
      {
         "id": "43a41e36-2ce4-4bb8-ac8b-ab410ded2159",
         "created": "2022-05-23T13:15:31.704607",
         "title": "ARSINOE Public Datasets",
         "description": "All datasets generated in the context of ARSINOE project and are public",
         "image_url": "https://arsinoe-project.eu/securstorage/2022/02/logo-2x.png",
         "number_of_datasets": 2
      }
   ]

===========
Retrieve Group Info
===========

.. http:get:: /group-info

   Retrieves the information of the specified group.
   
   :query string:  group_id (*required*) -- The id of the group.

   :requestheader Authorization: optional `API token`

   :statuscode 404: No group was found with the provided id.

**Example Request**

.. sourcecode:: bash

   curl --request GET '{serviceBaseURL}/group-info?group_id=43a41e36-2ce4-4bb8-ac8b-ab410ded2159' --header 'Authorization: token'

**Example Response**

.. sourcecode:: json

   {
      "id": "43a41e36-2ce4-4bb8-ac8b-ab410ded2159",
      "created": "2022-05-23T13:15:31.704607",
      "title": "ARSINOE Public Datasets",
      "description": "All datasets generated in the context of ARSINOE project and are public",
      "image_url": "https://arsinoe-project.eu/securstorage/2022/02/logo-2x.png",
      "number_of_datasets": 2
   }

===========
List Datasets per Group
===========

.. http:get:: /list-datasets-per-group

   Retrieves a list of datasets that belong to the specified group.
   
   :query string:  group_id (*required*) -- The id of the group.

   :requestheader Authorization: optional `API token`

   :statuscode 404: No group was found with the provided id.

**Example Request**

.. sourcecode:: bash

   curl --request GET '{serviceBaseURL}/list-datasets-per-group?group_id=43a41e36-2ce4-4bb8-ac8b-ab410ded2159' --header 'Authorization: token'

**Example Response**

.. sourcecode:: json

   [
      {
         "id": "db9e7f31-f93b-48fe-a571-8be5383f12c7",
         "title": "A test dataset",
         "name": "a-test-dataset",
         "description": "A test description",
         "author": "Author 2",
         "maintainer": "",
         "doi": "",
         "origin": "",
         "resources": [
            {
               "id": "2c9efc29-6df6-4d07-a9eb-399b08c64900",
               "created": "2022-10-21T09:27:15.854029",
               "description": "",
               "format": "CSV",
               "name": "",
               "size": null,
               "url": "http://example.com",
               "last_modified": null,
               "resource_type": null
            }
         ],
         "license_id": "gfdl",
         "license_title": "GNU Free Documentation License",
         "license_url": "http://www.opendefinition.org/licenses/gfdl",
         "private": false,
         "case_study_id": "408463d1-b4a2-40a6-bca6-5b69ee3b26a2",
         "number_of_resources": 1,
         "publication_date": "2022-10-22",
         "authorEmail": "",
         "maintainer_email": "",
         "resource_type": null,
         "dataset_type": ""
      },
      {
         "id": "5bcb70b5-0e6e-47eb-a99d-7e24a6f2d3c8",
         "title": "Athens historic center tree inventory",
         "name": "athens-historic-center-tree-inventory",
         "description": "Athens historic center tree inventory",
         "author": "Athens Municipality",
         "maintainer": "Athens Municipality",
         "doi": null,
         "origin": null,
         "resources": [
            {
               "id": "3a8ba553-ba2d-4720-bc41-e9faa1a87d6d",
               "created": "2022-06-21T10:31:45.602330",
               "description": "shape file of trees in the historic center of Athens",
               "format": "SHP",
               "name": "TREES_ISTORIC CENTER.zip",
               "size": 779413,
               "url": "{baseURL}/dataset/5bcb70b5-0e6e-47eb-a99d-7e24a6f2d3c8/resource/3a8ba553-ba2d-4720-bc41-e9faa1a87d6d/download/trees_istoric-center.zip",
               "last_modified": "2022-06-21T10:31:45.555831",
               "resource_type": null
            },
            {
               "id": "1d63af36-4743-44fe-a26a-a43b031d814a",
               "created": "2022-06-21T16:05:44.205243",
               "description": "",
               "format": "GeoJSON",
               "name": "test.geojson",
               "size": 6599826,
               "url": "{baseURL}/dataset/5bcb70b5-0e6e-47eb-a99d-7e24a6f2d3c8/resource/1d63af36-4743-44fe-a26a-a43b031d814a/download/test.geojson",
               "last_modified": "2022-06-21T16:05:44.153692",
               "resource_type": null
            },
            {
               "id": "df06320d-2e7c-4b74-9ca0-5ccd9eae4af2",
               "created": "2022-06-22T08:39:01.068204",
               "description": "",
               "format": "SHP",
               "name": "mydataset.zip",
               "size": 694781,
               "url": "{baseURL}/dataset/5bcb70b5-0e6e-47eb-a99d-7e24a6f2d3c8/resource/df06320d-2e7c-4b74-9ca0-5ccd9eae4af2/download/mydataset.zip",
               "last_modified": "2022-06-22T08:39:01.015468",
               "resource_type": null
            }
         ],
         "license_id": "cc-nc",
         "license_title": "Creative Commons Non-Commercial (Any)",
         "license_url": "http://creativecommons.org/licenses/by-nc/2.0/",
         "private": true,
         "case_study_id": "e5ad6b2d-3c93-4f1f-a143-6a18a4dc0955",
         "number_of_resources": 3,
         "publication_date": null,
         "authorEmail": "",
         "maintainer_email": "",
         "resource_type": null,
         "dataset_type": null
      },
   ]