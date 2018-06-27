# framework
android develop 框架
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
		    maven { url 'https://jitpack.io' }
		}
	}

	Step 2. Add the dependency

		dependencies {
    	    implementation 'com.github.xsgzt:framework:framework1.0'
    	}


   /**
     * 头部的使用
     **/

     protected void intTitle() {
            new DefaultNavigationBar.Builder(this).setTitle("ceishiactivity").build();
      }

      /**
       * 弹框的使用
       **/
      public void showDialog() {
         mDialog = new AlertDialog.Builder(AppManagerUtil.instance().currentActivity(), R.style.dialog_white)
                            .setContentView(R.layout.loading_dialog)
                             .setGravity(Gravity.CENTER)
                             .setCancelable(false)
                             .setWidthAndHeight((int) (ScreenUtils.getScreenWidth(AppManagerUtil.instance().currentActivity()) * 0.5), ViewGroup.LayoutParams.WRAP_CONTENT)
                             .create()
                             mDialog.show();
            }


        后续更新功能.....