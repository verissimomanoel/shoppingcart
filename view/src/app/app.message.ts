import { InternationalizationResource } from './shared/message/internationalization.resource';

/**
 * Implementation responsible for providing as 'descriptions' and 'messages' in the application in one place.
 *
 * @author Manoel Veríssimo
 */
export class AppMessage implements InternationalizationResource {

  private resource: Object;

  /**
   * Constructor of class.
   */
  constructor() {
    this.resource = {

      // LABEL
      'LABEL_OK': 'OK',
      'LABEL_SIM': 'Sim',
      'LABEL_NAO': 'Não',
      'LABEL_EMAIL': 'Email',
      'LABEL_ADMIN': 'Admin',
      'LABEL_USER': 'User',
      'LABEL_ITEM': 'Item',
      'LABEL_SHOPPING_CART': 'Shopping Cart',
      'LABEL_LEAVE': 'Leave',
      'LABEL_NAME': 'Name',
      'LABEL_SEARCH': 'Search',
      'LABEL_CLEAN': 'Clean',
      'LABEL_NEW': 'New',
      'LABEL_LOGIN': 'Login',
      'LABEL_DETAIL': 'Detail',
      'LABEL_UPDATE': 'Update',
      'LABEL_DELETE': 'Delete',
      'LABEL_PASSWORD': 'Password',
      'LABEL_SIGNIN': 'Sign in',
      'LABEL_WELCOME': 'Welcome',
      'LABEL_ACTIONS': 'Actions',
      'LABEL_SAVE': 'Save',
      'LABEL_CANCEL': 'Cancel',
      'LABEL_BACK': 'Back',
      'LABEL_VALUE': 'Value',
      'LABEL_ADD_ITEM': 'Add Item',
      'LABEL_MY_ITEMS': 'My Items',
      'LABEL_QUANTITY': 'Quantity',
      'LABEL_AMOUNT': 'Amount',
      'LABEL_ADD': 'Add',
      'LABEL_DEL': 'Del',
      'LABEL_CHECKOUT': 'Checkout',
      'LABEL_TOTAL': 'Total',


      //Title
      'TITLE_USER': 'User',
      'TITLE_NEW_USER': 'New User',
      'TITLE_UPDATE_USER': 'Update User',
      'TITLE_SEARCH_USER': 'Search User',
      'TITLE_DETAIL_USER': 'User Details',
      'TITLE_ITEM': 'Item',
      'TITLE_NEW_ITEM': 'New Item',
      'TITLE_UPDATE_ITEM': 'Update Item',
      'TITLE_SEARCH_ITEM': 'Search Item',
      'TITLE_DETAIL_ITEM': 'Item Details',
      'TITLE_SHOPPING_CART': 'Shopping Cart',

      // Confirm
      'LABEL_CONFIRM_OK': 'Ok',
      'LABEL_CONFIRM_NO': 'No',
      'LABEL_CONFIRM_YES': 'Yes',
      'LABEL_CONFIRM_TITLE': 'Confirmation',

      //Validation
      'required': 'Required field.',
      'minlength': 'Size must be between {0} and {1}',
      'email': 'Invalid email.',

      // Message
      'MSG001': 'Do you really want to leave?',
      'MSG002': 'Are you sure you want to delete this record?',
      'MSG003': 'No records found!',

      'MSG005': 'User successfully added!',
      'MSG006': 'User successfully updated!',
      'MSG007': 'User successfully deleted!',
      'MSG008': 'Item successfully added!',
      'MSG009': 'Item successfully updated!',
      'MSG010': 'Item successfully deleted!',
      'MSG011': 'Cart successfully created!',
      'MSG012': 'The item value needs to greater than 0!',
      'MSG013': 'Are you sure you want to delete this item?',
      'MSG014': 'Your cart is empty!',
    };
  }

  /**
   * Returns the 'description' according to the 'key' entered.
   *
   * @param key
   * @returns
   */
  getDescription(key: string): string {
    return this.resource[key];
  }

  /**
   * Returns the 'message' according to the 'key' entered.
   *
   * @param key
   * @returns
   */
  getMessage(key: string): string {
    return this.getDescription(key);
  }
}
