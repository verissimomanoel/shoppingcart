/**
 * Interface provider responsible for allowing the centralization of descriptions/messages used in the application in one place.
 *
 * @author Manoel Veríssimo
 */
export interface MessageResource {

  /**
   * Returns the 'description' according to the 'key' entered.
   *
   * @param key
   * @returns
   */
  getDescription(key: string): string;
}

/**
 * Provider interface responsible for providing instances of MessageResource.
 *
 * @author Manoel Veríssimo
 */
export interface MessageResourceProvider {

  /**
   * MessageResource instance factory.
   */
  new(): MessageResource;
}

/**
 * Provider class responsible for providing instances of MessageResource.
 *
 * @author Manoel Veríssimo
 */
export class MessageResourceProvider implements MessageResourceProvider { }