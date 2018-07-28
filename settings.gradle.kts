/*
 * Copyright [2018] [zhi]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// commons define
include(":common")
project(":common").projectDir = file("commons/common")

include(":common-multidex")
project(":common-multidex").projectDir = file("commons/multidex")

include(":common-net")
project(":common-net").projectDir = file("commons/net")

include(":common-glide")
project(":common-glide").projectDir = file("commons/glide")

include(":common-firebase")
project(":common-firebase").projectDir = file("commons/firebase")

// services common
include(":service-neo-common")
project(":service-neo-common").projectDir = file("services/neo-common")

//include(":service-neo-sonar")
//project(":service-neo-sonar").projectDir = file("services/neo-sonar")

include(":service-neo-bugly")
project(":service-neo-bugly").projectDir = file("services/neo-bugly")

include(":service-neo")
project(":service-neo").projectDir = file("services/neo")

include(":service-auth-common")
project(":service-auth-common").projectDir = file("services/auth-common")

include(":service-auth-anonymous")
project(":service-auth-anonymous").projectDir = file("services/auth-anonymous")

include(":service-auth-facebook")
project(":service-auth-facebook").projectDir = file("services/auth-facebook")

include(":service-auth-google")
project(":service-auth-google").projectDir = file("services/auth-google")

include(":service-auth-phone")
project(":service-auth-phone").projectDir = file("services/auth-phone")

include(":service-auth-email")
project(":service-auth-email").projectDir = file("services/auth-email")

include(":service-auth-twitter")
project(":service-auth-twitter").projectDir = file("services/auth-twitter")

include(":service-auth-github")
project(":service-auth-github").projectDir = file("services/auth-github")

include(":service-auth")
project(":service-auth").projectDir = file("services/auth")

// modules define
include(":module-splash")
project(":module-splash").projectDir = file("modules/splash")

include(":module-profile")
project(":module-profile").projectDir = file("modules/profile")

include(":module-share-common")
project(":module-share-common").projectDir = file("modules/share-common")

include(":module-share")
project(":module-share").projectDir = file("modules/share")

// apps define
include(":app-xlap")
project(":app-xlap").projectDir = file("apps/xlap")

////include(":app-group")
////project(":app-group").projectDir = file("apps/group")
////
////include(":app-cupid")
////project(":app-cupid").projectDir = file("apps/cupid")
