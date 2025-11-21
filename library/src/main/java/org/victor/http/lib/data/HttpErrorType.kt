package org.victor.http.lib.data

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2025-2035, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpErrorType
 * Author: Victor
 * Date: 2025/11/21 10:20
 * Description: 
 * -----------------------------------------------------------------
 */

object HttpErrorType {
 /**
  * Represents failure response (non-2xx) with body.
  */
 const val API = 0x423001

 /**
  *  Represents network failure (such as no internet connection).
  */
 const val NETWORK = 0x423002

 /**
  * Represents unexpected exceptions (for example parsing issues).
  */
 const val UNKNOWN = 0x423003

 /**
  * server error (such as body == null or errorBody == null).
  */
 const val SERVER = 0x423004

 /**
  * data is empty
  */
 const val EMPTY = 0x423005
}