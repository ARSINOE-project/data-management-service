#############
Documentation for the Arsinoe Data Management Service
#############

.. |catalogueBaseURL| replace:: https://catalogue.arsinoe-project.eu
.. |serviceBaseURL| replace:: https://catalogue.arsinoe-project.eu/data-management-service


The Arsinoe Data Management Service provides endpoints to simplify the machine-to-machine interaction with the Arsinoe Data Catalogue.

.. contents:: Table of Contents
   :local:
   :backlinks: none


*************
Data Management Service Endpoints
*************
The endpoints listed below may also receive an API token in the Authorization header. An API token can be issued by navigating to the profile page and clicking on the API Tokens tab. Alternatively, the auto-generated API Key found at the bottom-left corner of the profile page can be used. Including a token in the requests identifies the user and yields results according to the user's rights on the platform. Otherwise, only public elements of the platform will be returned. 
The base URL of the Data Catalogue is |catalogueBaseURL| and the base URL of the data management service is |serviceBaseURL|

===========
List Case Studies
===========

.. http:get:: /list-case-studies
   
   Retrieves a list of the case studies in the Data Catalogue.
   
   :requestheader Authorization: `API token` (*optional*)

**Example Request**

.. sourcecode:: bash
  
    curl --request GET ' {serviceBaseURL}/list-case-studies' --header 'Authorization: token'

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

   Retrieves a paginated list of the datasets in the Data Catalogue. In the case of a protected dataset on which the user does not have full view rights, the resources only include the name field.

   :query string case_study_id: The id of the case study. Specifies the case study to which the datasets belong. If not provided, then all datasets in the catalogue are returned. (*optional*)

   :query int page: The page number.

   :requestheader Authorization: `API token` (*optional*)

   :statuscode 404: The provided `case_study_id` does not match a case study in the Data Catalogue.

**Example Request**

.. sourcecode:: bash
  
   curl --request GET '{serviceBaseURL}/list-datasets' --header 'Authorization: token'

**Example Response**

.. sourcecode:: json

   {
      "results": [
         {
            "id": "6e6e41d9-c7f4-4472-87d7-acc458737a86",
            "title": "Greece Shapefile",
            "name": "greece-shapefile",
            "description": "Greece",
            "author": "EEA",
            "maintainer": "",
            "doi": "",
            "origin": "secondary",
            "tags": [],
            "resources": [
                  {
                     "id": "333ec029-6299-4f3e-8912-953228026d33",
                     "created": "2023-05-08T12:52:35.285792",
                     "description": "",
                     "format": "SHP",
                     "name": "Greece_shapefile",
                     "size": 18352169,
                     "url": "{catalogueBaseURL}/dataset/6e6e41d9-c7f4-4472-87d7-acc458737a86/resource/333ec029-6299-4f3e-8912-953228026d33/download/greece_shapefile.zip",
                     "last_modified": "2023-05-08T12:52:35.265641",
                     "resource_type": null
                  }
            ],
            "license_id": "other-open",
            "license_title": "Other (Open)",
            "license_url": null,
            "visibility": "public",
            "zenodo_publish": false,
            "case_study_id": "b022e32a-7212-4685-a923-a22b6787f20b",
            "number_of_resources": 1,
            "publication_date": null,
            "author_email": "",
            "maintainer_email": "",
            "resource_type": "observational",
            "dataset_type": "geospatial"
         },
         {
            "id": "71f3d3fc-d707-4db2-b951-020abc1418ff",
            "title": "Natura 2000 greece",
            "name": "natura-2000-greece",
            "description": "Natura 2000 greece",
            "author": "Natura",
            "maintainer": "",
            "doi": "",
            "origin": "secondary",
            "tags": [],
            "resources": [
                  {
                     "id": "7b32d161-6d1b-4938-a671-cb3ef302e905",
                     "created": "2023-05-08T12:49:05.615586",
                     "description": "Natura 200 shape file",
                     "format": "SHP",
                     "name": "Natura 200 shape file",
                     "size": 20713988,
                     "url": "{catalogueBaseURL}/dataset/71f3d3fc-d707-4db2-b951-020abc1418ff/resource/7b32d161-6d1b-4938-a671-cb3ef302e905/download/20200813_natura2000_shape-files.rar",
                     "last_modified": "2023-05-08T12:49:05.584061",
                     "resource_type": null
                  }
            ],
            "license_id": "other-open",
            "license_title": "Other (Open)",
            "license_url": null,
            "visibility" "public",
            "zenodo_publish": true,
            "case_study_id": "b022e32a-7212-4685-a923-a22b6787f20b",
            "number_of_resources": 1,
            "publication_date": null,
            "author_email": "",
            "maintainer_email": "",
            "resource_type": "observational",
            "dataset_type": "geospatial"
         },
         {
            "id": "194e0bf4-db9d-41f3-af90-b7fecacd9d1b",
            "title": "Natura 2000 network",
            "name": "natura-2000-network",
            "description": "Natura 2000 is the key instrument to protect biodiversity in the European Union. It is an ecological network of protected areas, set up to ensure the survival of Europe's most valuable species and habitats. Natura 2000 is based on the 1979 Birds Directive and the 1992 Habitats Directive. This version covers the reporting in 2020.",
            "author": "Directorate-General for Environment (DG ENV) / European Environment Agency (EEA)",
            "maintainer": "",
            "doi": "",
            "origin": "secondary",
            "tags": [
                  "tag1",
                  "tag2",
                  "tag3"
            ],
            "resources": [
                  {
                     "id": "0e74b107-b9d7-4a9b-921b-82f83dcb49e0",
                     "created": "2023-04-21T08:05:38.269019",
                     "description": "",
                     "format": "",
                     "name": "NATURA 2000",
                     "size": null,
                     "url": "https://www.eea.europa.eu/data-and-maps/data/natura-14",
                     "last_modified": null,
                     "resource_type": null
                  }
            ],
            "license_id": "other-open",
            "license_title": "Other (Open)",
            "license_url": null,
            "visibility": "public",
            "zenodo_publish": null,
            "case_study_id": "b022e32a-7212-4685-a923-a22b6787f20b",
            "number_of_resources": 1,
            "publication_date": null,
            "author_email": "",
            "maintainer_email": "",
            "resource_type": "observational",
            "dataset_type": "geospatial"
         }
      ],
      "pageMetadata": {
         "size": 3,
         "totalElements": 3,
         "totalPages": 1,
         "number": 0
      }
   }

===========
Search Datasets
===========

.. http:get:: /search-datasets

   Retrieves a paginated list of the datasets in the Data Catalogue. The query parameters listed below can be repeated, but when using the "and" operator it only
   makes sense for tags to be repeated, since a dataset may have multiple tags but not e.g. multiple authors or origins.

   :query int page: The page number.

   :query string operator: The operator that joins the filtered fields. Can either be "or" or "and". Defaults to "and". (*optional*)

   :query string author: The author of the dataset. (*optional*)

   :query string dataset_type: Check create dataset for available options.  (*optional*)

   :query string origin: Check create dataset for available options. (*optional*)

   :query string resource_type: Check create dataset for available options. (*optional*)

   :query string id: The id of the dataset (*optional*)

   :query string name: The name of the dataset (*optional*)

   :query string case_study_name: The name of the case study. (*optional*)

   :query string title: The title of the dataset (*optional*)

   :query string tags: The tags of the dataset. (*optional*)

   :query string resource_format: The file format of resources within the dataset. (*optional*)

   :requestheader Authorization: `API token` (*optional*)

**Example Request**

.. sourcecode:: bash
  
   curl --request GET '{serviceBaseURL}/search-datasets?tags=tag1&tags=tag2&author=some_name&operator=and' --header 'Authorization: token'

**Example Response**

Same as in `List Datasets`_.

===========
Retrieve Dataset Info
===========

.. http:get:: /dataset-info

   Retrieves the information of the specified dataset.

   :query string dataset_id: The id of the dataset.

   :requestheader Authorization: `API token` (*optional*)

   :statuscode 404: No dataset was found with the provided id.

**Example Request**

.. sourcecode:: bash
  
   curl --request GET '{serviceBaseURL}/dataset-info?dataset_id=d551d2ff-0902-4576-a610-942e230faaa4' --header 'Authorization: token'

**Example Response**

Same as in `List Datasets`_ but instead of a list, returns a single object.

===========
Retrieve Resource Info
===========

.. http:get:: /resource-info

   Retrieves the information of the specified resource.

   :query string resource_id: The id of the resource.

   :requestheader Authorization: `API token` (*optional*)

   :statuscode 404: No resource was found with the provided id.

**Example Request**

.. sourcecode:: bash
  
   curl --request GET '{serviceBaseURL}/resource-info?resource_id=dea64b6b-5bf7-4698-bb88-d095be9c4ccb' --header 'Authorization: token'

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

   :query string resource_id: The id of the resource.

   :requestheader Authorization: `API token` (*optional*)

   :statuscode 301: The resource download url was retrieved successfully.

   :statuscode 404: No resource was found with the provided id.

**Example Request**

.. sourcecode:: bash
  
   curl --location --request GET '{serviceBaseURL}/resource-info?resource_id=dea64b6b-5bf7-4698-bb88-d095be9c4ccb' --header 'Authorization: token'

.. note::

   The ``--location`` option instructs curl to follow redirects.

===========
List Groups
===========

.. http:get:: /list-groups

   Retrieves a list of the groups in the Data Catalogue.

   :requestheader Authorization: `API token` (*optional*)

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
   
   :query string group_id: The id of the group.

   :requestheader Authorization: `API token` (*optional*)

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
   
   :query string group_id: The id of the group.

   :requestheader Authorization: `API token` (*optional*)

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
         "visibility": "public",
         "zenodo_publish": null,
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
               "url": "{catalogueBaseURL}/dataset/5bcb70b5-0e6e-47eb-a99d-7e24a6f2d3c8/resource/3a8ba553-ba2d-4720-bc41-e9faa1a87d6d/download/trees_istoric-center.zip",
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
               "url": "{catalogueBaseURL}/dataset/5bcb70b5-0e6e-47eb-a99d-7e24a6f2d3c8/resource/1d63af36-4743-44fe-a26a-a43b031d814a/download/test.geojson",
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
               "url": "{catalogueBaseURL}/dataset/5bcb70b5-0e6e-47eb-a99d-7e24a6f2d3c8/resource/df06320d-2e7c-4b74-9ca0-5ccd9eae4af2/download/mydataset.zip",
               "last_modified": "2022-06-22T08:39:01.015468",
               "resource_type": null
            }
         ],
         "license_id": "cc-nc",
         "license_title": "Creative Commons Non-Commercial (Any)",
         "license_url": "http://creativecommons.org/licenses/by-nc/2.0/",
         "visibility": "public",
         "zenodo_publish": null,
         "case_study_id": "e5ad6b2d-3c93-4f1f-a143-6a18a4dc0955",
         "number_of_resources": 3,
         "publication_date": null,
         "authorEmail": "",
         "maintainer_email": "",
         "resource_type": null,
         "dataset_type": null
      },
   ]

===========
List Licenses
===========

.. http:get:: /list-licenses
   
   Retrieves a list of all of the available licenses in the catalogue. At the time of this documentation being written, including an authorization token does not make a difference on the results.

**Example Request**

.. sourcecode:: bash

   curl --request GET '{serviceBaseURL}/list-licenses --header 'Authorization: token'

**Example Response**

.. sourcecode:: json

   [
      {
         "id": "notspecified",
         "family": "",
         "maintainer": "",
         "status": "active",
         "url": "",
         "title": "License not specified",
         "domain_content": "False",
         "domain_data": "False",
         "domain_software": "False",
         "is_generic": "True",
         "od_conformance": "not reviewed",
         "osd_conformance": "not reviewed",
         "is_okd_compliant": false,
         "is_osi_compliant": false
      },
      {
         "id": "odc-pddl",
         "family": "",
         "maintainer": "",
         "status": "active",
         "url": "http://www.opendefinition.org/licenses/odc-pddl",
         "title": "Open Data Commons Public Domain Dedication and License (PDDL)",
         "domain_content": "False",
         "domain_data": "True",
         "domain_software": "False",
         "is_generic": "False",
         "od_conformance": "approved",
         "osd_conformance": "not reviewed",
         "is_okd_compliant": true,
         "is_osi_compliant": false
      },
      {
         "id": "odc-odbl",
         "family": "",
         "maintainer": "",
         "status": "active",
         "url": "http://www.opendefinition.org/licenses/odc-odbl",
         "title": "Open Data Commons Open Database License (ODbL)",
         "domain_content": "False",
         "domain_data": "True",
         "domain_software": "False",
         "is_generic": "False",
         "od_conformance": "approved",
         "osd_conformance": "not reviewed",
         "is_okd_compliant": true,
         "is_osi_compliant": false
      },
      {
         "id": "odc-by",
         "family": "",
         "maintainer": "",
         "status": "active",
         "url": "http://www.opendefinition.org/licenses/odc-by",
         "title": "Open Data Commons Attribution License",
         "domain_content": "False",
         "domain_data": "True",
         "domain_software": "False",
         "is_generic": "False",
         "od_conformance": "approved",
         "osd_conformance": "not reviewed",
         "is_okd_compliant": true,
         "is_osi_compliant": false
      },
      {
         "id": "cc-zero",
         "family": "",
         "maintainer": "",
         "status": "active",
         "url": "http://www.opendefinition.org/licenses/cc-zero",
         "title": "Creative Commons CCZero",
         "domain_content": "True",
         "domain_data": "True",
         "domain_software": "False",
         "is_generic": "False",
         "od_conformance": "approved",
         "osd_conformance": "not reviewed",
         "is_okd_compliant": true,
         "is_osi_compliant": false
      }
   ]

===========
List Dataset Origins
===========

.. http:get:: /list-dataset-origins
   
   Retrieves a list of all of the available dataset origins that can be accepted when creating a dataset. At the time of this documentation being written, including an authorization token does not make a difference on the results.

**Example Request**

.. sourcecode:: bash

   curl --request GET '{serviceBaseURL}/list-dataset-origins --header 'Authorization: token'

**Example Response**

.. sourcecode:: json

   [
      "unknown",
      "primary",
      "secondary"
   ]

===========
List Dataset Origins
===========

.. http:get:: /list-dataset-resource-types
   
   Retrieves a list of all of the available dataset resource types that can be accepted when creating a dataset. At the time of this documentation being written, including an authorization token does not make a difference on the results.

**Example Request**

.. sourcecode:: bash

   curl --request GET '{serviceBaseURL}/list-dataset-resource-types --header 'Authorization: token'

**Example Response**

.. sourcecode:: json

   [
      "model",
      "software",
      "sensor",
      "observational",
      "report",
      "images",
      "formulas",
      "statistical"
   ]

===========
List Dataset Types
===========

.. http:get:: /list-dataset-types
   
   Retrieves a list of all of the available dataset types that can be accepted when creating a dataset. At the time of this documentation being written, including an authorization token does not make a difference on the results.

**Example Request**

.. sourcecode:: bash

   curl --request GET '{serviceBaseURL}/list-dataset-types --header 'Authorization: token'

**Example Response**

.. sourcecode:: json

   [
      "textual",
      "geospatial",
      "satellite_images",
      "tabular",
      "video",
      "scripts"
   ]


===========
Create Dataset
===========

.. http:post:: /create-dataset
   
   Creates a new dataset in the Data Catalogue. (A dataset is a collection of resources)
   
   :requestheader Authorization: `API token`
   :<json string title: The title of the dataset.
   :<json string name: The name of the dataset (This will be used to create the url of the dataset within the catalogue. Use all lowercase letters and hyphens instead of spaces)
   :<json string description: The description of the dataset. (*optional*)
   :<json string license_id: The id of the license. See /list-licenses for available values. (*optional*)
   :<json string publication_date: The publication date of the dataset. (*optional*)
   :<json string author: The name of the dataset's author.
   :<json string author_email: The author's email. (*optional*)
   :<json string maintainer: The name of the dataset's maintainer. (*optional*)
   :<json string maintainer_email: The email of the dataset's maintainer. (*optional*)
   :<json string doi: The DOI of the dataset. (*optional*)
   :<json string origin: The origin of the dataset. Can be one of: unknown, primary, secondary
   :<json string resource_type: The resource type of the dataset. Can be one of: model, software, sensor, observational, report, images, formulas, statistical
   :<json string dataset_type: The type of the dataset. Can be one of: textual, geospatial, satellite_images, tabular, video, scripts
   :<json string array tags: The tags of the dataset. An array of strings. (*optional*)
   :<json string case_stuy_id: The id of the owner case study.
   :<json string visibility: The visiblity of the dataset. Can be public, private or protected. (required) 


**Example Request**

.. sourcecode:: bash
  
    curl --request POST ' {serviceBaseURL}/create-dataset' --header 'Authorization: token' --header "Content-Type: application/json" --data @body.json

**Example Body**

.. sourcecode:: json

   {
      "title": "Dataset title",
      "name": "a-new-dataset",
      "author": "John D. Author",
      "dataset_type": "textual",
      "license_id": "cc-by",
      "description": "Description of the new dataset",
      "origin": "primary",
      "case_study_id": "uuid-of-case-study",
      "resource_type": "software",
      "tags": ["tag1", "tag2", "tag3"],
      "publication_date": "2023/02/28",
      "visibility": "protected",
      "zenodo_publish": true
   }

**Response**

Returns the newly created dataset in the same format as *Dataset Info*

===========
Update Dataset
===========

.. http:put:: /update-dataset
   
   Updates an existing dataset. In addition to the fields found in /create-dataset, there must also be an id present referring to the id of the dataset being updated.

   :statuscode 404: No dataset was found with the provided id.

===========
Patch Dataset
===========

.. http:patch:: /patch-dataset
   
   Patches an existing dataset. The difference with /update-dataset is that this method allows partial updates on the dataset. Only the provided fields will be
   updated. Omitted or null fields will be ignored.

   :statuscode 404: No dataset was found with the provided id.

===========
Delete Dataset
===========

.. http:delete:: /delete-dataset

   Deletes the specified dataset.

   :query string dataset_id: The id of the dataset.

   :requestheader Authorization: `API token`

   :statuscode 404: No dataset was found with the provided id.
   
   :statuscode 200: The dataset was deleted successfully.

**Example Request**

.. sourcecode:: bash
  
   curl --request DELETE '{serviceBaseURL}/delete-dataset?dataset_id=dea64b6b-5bf7-4698-bb88-d095be9c4ccb' --header 'Authorization: token'


===========
Create Resource
===========

.. http:post:: /create-resource
   
   Creates a new dataset in the Data Catalogue. (A dataset is a collection of resources)
   
   :requestheader Authorization: `API token`
   :formparam file: The resource file. (*optional*)
   :formparam resource: A json string containing the rest of the fields listed below:
   :<json string dataset_id: The id of the owning dataset. (Inside resource json)
   :<json string url: The url to an external resource. This must be provided only if a file upload is not provided. (*optional*) (Inside resource json)
   :<json string description: The description of the resource. (*optional*) (Inside resource json)
   :<json string format: The format of the resource, e.g. csv, shp, html etc (*optional*) (Inside resource json)
   :<json string name: The name of the resource. (Inside resource json)
     


**Example Request**

.. sourcecode:: bash
  
    curl --request POST ' {serviceBaseURL}/create-resource' --header 'Authorization: token' \
      --form 'file=@"path/to/file"' \
      --form 'resource="{\"dataset_id\": \"eebc4b25-da99-43f8-8221-e12a13cd9da4\", \"name\": \"Resource 10\", \"description\": \"A description\", \"format\": \"csv\"}"'

**Example Multipart form Data for "resource"**

.. sourcecode:: json

   {
      "dataset_id": "eebc4b25-da99-43f8-8221-e12a13cd9da4",
      "name": "Resource 10",
      "description": "A description",
      "format": "csv"
   }

**Response**

Returns the newly created resource in the same format as *Resource Info*

===========
Update Resource
===========

.. http:put:: /update-resource
   
   Updates an existing resource. If a resource was created with an external url, you may only change the external url. If it was created with a file upload, you may only change the file upload.

   :statuscode 404: No resource was found with the provided id.


===========
Patch Resource
===========

.. http:patch:: /patch-resource
   
   Patches an existing resource. The difference with update is the same as in /update-dataset and /patch-dataset.

   :statuscode 404: No resource was found with the provided id.

===========
Delete Resource
===========

.. http:delete:: /delete-resource

   Deletes the specified dataset.

   :query string resource_id: The id of the resource.

   :requestheader Authorization: `API token`

   :statuscode 404: No resource was found with the provided id.
   
   :statuscode 200: The resource was deleted successfully.

**Example Request**

.. sourcecode:: bash
  
   curl --request DELETE '{serviceBaseURL}/delete-resource?resource_id=dea64b6b-5bf7-4698-bb88-d095be9c4ccb' --header 'Authorization: token'
