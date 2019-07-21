using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Inflate.RNInflate
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNInflateModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNInflateModule"/>.
        /// </summary>
        internal RNInflateModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNInflate";
            }
        }
    }
}
