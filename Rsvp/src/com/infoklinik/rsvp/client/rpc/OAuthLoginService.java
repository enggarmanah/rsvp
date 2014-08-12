/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.infoklinik.rsvp.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.client.OAuthException;
import com.infoklinik.rsvp.shared.Credential;
import com.infoklinik.rsvp.shared.SocialUser;

@RemoteServiceRelativePath("oAuthLoginService")
public interface OAuthLoginService extends RemoteService {
	public String getAuthorizationUrl(Credential credential)
			throws OAuthException;

	public SocialUser verifySocialUser(Credential credential) throws OAuthException;

	public SocialUser getSocialUser(String sessionId) throws OAuthException;

	public SocialUser getSocialUserAfterVerification() throws OAuthException;
	
	public String getAccessToken(String sessionId) throws OAuthException;

	public String getSessionId() throws OAuthException;
}
