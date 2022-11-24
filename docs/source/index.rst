Documentation for the Arsinoe Data Management Service
===================================

The Arsinoe Data Management Service provides endpoints to simplify the machine-to-machine interaction with the Arsinoe Data Catalogue.

Data Management Service Endpoints
--------------------------------
The endpoints listed below may also receive an API token in the Authorization header. An API token can be issued by navigating to the profile page and clicking on the API Tokens tab. Alternatively, the auto-generated API Key, found at the bottom-left corner of the screen of the profile can be used. Including a token in the requests identifies the user and yields results according to the user's rights on the platform. Otherwise, only public elements of the platform will be returned. 

List Case Studies
--------------------------

.. http:post:: /list-case-studies
   
      Lists the case studies of the catalogue.
   
   :requestheader Authorization: `token`

.. http:post:: /list-datasets

      Lists the datasets of the catalogue.

   :query string:  case_study_id (*optional*) -- The id of the case study. Specifies the case study to which the datasets belong. If not provided, then all datasets in the catalogue are returned.

   :requestheader Authorization: `token`

.. http:post:: /dataset-info

      Returns the information of the specified dataset.

   :query string:  dataset_id (*required*) -- The id of the dataset.

   :requestheader Authorization: `token`

The Arsinoe Data Management Service has its documentation hosted on Read the Docs.
