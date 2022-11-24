#############
Documentation for the Arsinoe Data Management Service
#############

.. |baseURL| replace:: https://arsinoe-dev.madgik.di.uoa.gr/data-management-service


The Arsinoe Data Management Service provides endpoints to simplify the machine-to-machine interaction with the Arsinoe Data Catalogue.

*************
Data Management Service Endpoints
*************
The endpoints listed below may also receive an API token in the Authorization header. An API token can be issued by navigating to the profile page and clicking on the API Tokens tab. Alternatively, the auto-generated API Key, found at the bottom-left corner of the screen of the profile can be used. Including a token in the requests identifies the user and yields results according to the user's rights on the platform. Otherwise, only public elements of the platform will be returned. 
The base url for all the endpoints is |baseURL|

===========
List Case Studies
===========

.. http:get:: /list-case-studies
   
      Retrieves a list of the case studies in the Data Catalogue.
   
   :requestheader Authorization: optional `API token`

**Example Request**

.. sourcecode:: bash
  
    curl --request GET ' |baseURL| /list-case-studies' --header 'Authorization: token'

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

===========
Retrieve Dataset Info
===========

.. http:get:: /dataset-info

      Retrieves the information of the specified dataset.

   :query string:  dataset_id (*required*) -- The id of the dataset.

   :requestheader Authorization: optional `API token`

   :statuscode 404: No dataset was found with the provided id.

===========
Retrieve Resource Info
===========

.. http:get:: /resource-info

      Retrieves the information of the specified resource.

   :query string:  resource_id (*required*) -- The id of the resource.

   :requestheader Authorization: optional `API token`

   :statuscode 404: No resource was found with the provided id.

===========
Download Resource
===========

.. http:get:: /download-resource

      Redirects to the download url of the resource in the Data Catalogue.

   :query string:  resource_id (*required*) -- The id of the resource.

   :requestheader Authorization: optional `API token`

   :statuscode 302: The resource download url was retrieved successfully.

   :statuscode 404: No resource was found with the provided id.

===========
List Groups
===========

.. http:get:: /list-groups

      Retrieves a list of the groups in the Data Catalogue.

   :requestheader Authorization: optional `API token`

===========
Retrieve Group Info
===========

.. http:get:: /group-info

      Retrieves the information of the specified group.
   
   :query string:  group_id (*required*) -- The id of the group.

   :requestheader Authorization: optional `API token`

   :statuscode 404: No group was found with the provided id.

===========
List Datasets per Group
===========

.. http:get:: /list-datasets-per-group

      Retrieves a list of datasets that belong to the specified group.
   
   :query string:  group_id (*required*) -- The id of the group.

   :requestheader Authorization: optional `API token`

   :statuscode 404: No group was found with the provided id.